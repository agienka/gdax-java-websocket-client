package com.missd.gdaxjavawebsocketclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.missd.gdaxjavawebsocketclient.message.MessageType;
import com.missd.gdaxjavawebsocketclient.message.Subscription;
import com.missd.gdaxjavawebsocketclient.message.handler.GdaxMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.Future;

import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.TYPE;


@ClientEndpoint
public class GdaxWebsocketClient {

    private static Logger logger = LoggerFactory.getLogger(GdaxWebsocketClient.class);
    private final Map<MessageType, GdaxMessageHandler> messageHandlers;
    private final AuthAttributes authAttributes;
    private final Clock clock;
    private final ObjectReader objectReader;
    private final ObjectWriter objectWriter;

    private Session webSocketSession;

    public GdaxWebsocketClient(Map<MessageType, GdaxMessageHandler> messageHandlers, AuthAttributes authAttributes, Clock clock) {
        this.messageHandlers = messageHandlers;
        this.authAttributes = authAttributes;
        this.clock = clock;
        ObjectMapper objectMapper = new ObjectMapper();
        this.objectReader = objectMapper.readerFor(new TypeReference<Map<String, Object>>() {});
        this.objectWriter = objectMapper.writer();
    }

    public Future<Void> subscribe(Subscription subscription) {
        try {
            String signedMessage = signedMessage(subscription);
            return sendMessage(signedMessage);
        } catch (Exception e) {
            throw new GdaxWebsocketClientException("Could not prepare subscription.", e);
        }
    }

    private String signedMessage(Subscription subscription) throws MessageSignatureGenerationException {
        long signatureTimestamp = Instant.now(clock).getEpochSecond();
        String signature = MessageSignature.generate("", signatureTimestamp, authAttributes.getSecret());
        subscription.updateTimestamp(signatureTimestamp);
        subscription.sign(signature, authAttributes.getPassphrase(), authAttributes.getKey());
        return messageAsString(subscription);
    }

    private Future<Void> sendMessage(String message) {
        return this.webSocketSession.getAsyncRemote().sendText(message);
    }

    private String messageAsString(Object message) {
        String serialized;
        try {
            serialized = objectWriter.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new GdaxWebsocketClientException("Cannot parse message to be send", message, e);
        }
        return serialized;
    }

    @OnMessage
    public void onMessage(String message) {
        try {
            Map<String, Object> messageAsMap = objectReader.readValue(message);

            MessageType.valueOf(messageAsMap.get(TYPE)).ifPresent(messageType -> handleMessage(messageAsMap, messageType));

        } catch (Exception e) {
            throw new GdaxWebsocketClientException("Exception occured while receiving a message", message, e);
        }
    }

    private void handleMessage(Map<String, Object> message, MessageType messageType) {

        try {
            Class<?> messageTypeClass = messageType.getTypeClass(); //Type.ticker -> Ticker.class
            Method m = messageTypeClass.getMethod("from", Map.class); //Ticker.from(Map) -> Method
            Object parsedMsg = m.invoke(messageTypeClass, message); //invoke(Ticker.class, Map<String, Object>) -> Object

            GdaxMessageHandler messageHandler = messageHandlers.get(messageType);
            if (messageHandler != null) {
                messageHandler.handleMessage(parsedMsg);
            } else {
                logger.info("{}", message);
                logger.warn("No message handler registered to handle message of type {}. Try implementing {} for this MessageType", messageType, GdaxMessageHandler.class.getSimpleName());
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new GdaxWebsocketClientException("Exception occured while parsing incoming message", message, e);
        }
    }

    @OnOpen
    public void onOpen(Session webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    @OnClose
    public void onClose(Session webSocketSession, CloseReason reason) {
        logger.info("Closing websocket. Reason: {}", reason);
    }

    public boolean isConnected() {
        return this.webSocketSession !=null && this.webSocketSession.isOpen();
    }
}
