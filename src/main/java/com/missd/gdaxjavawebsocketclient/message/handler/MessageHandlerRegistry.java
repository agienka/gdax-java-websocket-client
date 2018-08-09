package com.missd.gdaxjavawebsocketclient.message.handler;

import com.missd.gdaxjavawebsocketclient.message.MessageType;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class MessageHandlerRegistry {

    private final Map<MessageType, GdaxMessageHandler> messageHandlers = new EnumMap<>(MessageType.class);

    public MessageHandlerRegistry() {
        messageHandlers.put(MessageType.error, new GdaxErrorMessageHandler());
    }

    public MessageHandlerRegistry addMessageHandler(MessageType forType, GdaxMessageHandler messageHandler) {
        this.messageHandlers.put(forType, messageHandler);
        return this;
    }

    public Map<MessageType, GdaxMessageHandler> getMessageHandlers() {
        return Collections.unmodifiableMap(messageHandlers);
    }
}
