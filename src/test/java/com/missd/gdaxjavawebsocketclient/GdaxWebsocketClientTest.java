package com.missd.gdaxjavawebsocketclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.missd.gdaxjavawebsocketclient.message.ChannelName;
import com.missd.gdaxjavawebsocketclient.message.MessageType;
import com.missd.gdaxjavawebsocketclient.message.Subscription;
import com.missd.gdaxjavawebsocketclient.message.handler.GdaxErrorMessageHandler;
import com.missd.gdaxjavawebsocketclient.message.handler.GdaxMessageHandler;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class GdaxWebsocketClientTest extends GdaxClientTest {

    private GdaxWebsocketClient gdaxWebsocketClient;

    private Map<MessageType, GdaxMessageHandler> messageHandlers;

    @Before
    public void setUp() throws Exception {
        messageHandlers = new HashMap<>();
        AuthAttributes authAttributes = new AuthAttributes();
        authAttributes.setKey(TEST_KEY);
        authAttributes.setSecret(TEST_SECRET);
        authAttributes.setPassphrase(TEST_PASSPHRASE);
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        gdaxWebsocketClient = new GdaxWebsocketClient(messageHandlers, authAttributes, clock);
    }

    public List<HandleMessageTestParams> handleMessageparameters() {
        return Arrays.asList(
                new HandleMessageTestParams(MessageType.ticker, ticker_message, mock(TestTickerMessageHandler.class)),
                new HandleMessageTestParams(MessageType.heartbeat, heartbeat_message, mock(TestHeartbeatMessageHandler.class)),
                new HandleMessageTestParams(MessageType.error, error_message, mock(GdaxErrorMessageHandler.class)),
                new HandleMessageTestParams(MessageType.snapshot, snapshot_message, mock(TestSnapshotMessageHandler.class)),
                new HandleMessageTestParams(MessageType.l2update, l2update_message, mock(TestL2UpdateMessageHandler.class)),
                new HandleMessageTestParams(MessageType.activate, activate_message, mock(TestActivateMessageHandler.class)),
                new HandleMessageTestParams(MessageType.change, change_message, mock(TestChangeMessageHandler.class)),
                new HandleMessageTestParams(MessageType.change, change_message2, mock(TestChangeMessageHandler.class)),
                new HandleMessageTestParams(MessageType.done, done_message, mock(TestDoneMessageHandler.class)),
                new HandleMessageTestParams(MessageType.open, open_message, mock(TestOpenMessageHandler.class)),
                new HandleMessageTestParams(MessageType.match, match_message, mock(TestMatchMessageHandler.class)),
                new HandleMessageTestParams(MessageType.received, received_message, mock(TestReceivedMessageHandler.class)),
                new HandleMessageTestParams(MessageType.received, received2_message, mock(TestReceivedMessageHandler.class)),
                new HandleMessageTestParams(MessageType.subscriptions, subscriptions, mock(TestSubscriptionMessageHandler.class))
        );
    }

    @Test
    @Parameters(method = "handleMessageparameters")
    public void shouldHandleAllDeclaredMessageTypes(HandleMessageTestParams params) {
        //given
        messageHandlers.put(params.messageType, params.messageHandler);

        //when
        gdaxWebsocketClient.onMessage(params.message);

        //then
        verify(params.messageHandler).handleMessage(any(params.messageType.getTypeClass()));
    }

    @Test
    public void shouldThrowClassCastExceptionIfWrongHandlerTypeSelected() {
        //given
        messageHandlers.put(MessageType.ticker, mock(GdaxErrorMessageHandler.class));

        //when
        Throwable thrown = catchThrowable(() -> gdaxWebsocketClient.onMessage(ticker_message));

        //then
        assertThat(thrown).isInstanceOf(GdaxWebsocketClientException.class);
        assertThat(thrown.getCause()).isInstanceOf(ClassCastException.class);
    }

    @Test
    public void shouldDoNothingForNewMessageType() {
        //when
        Throwable thrown = catchThrowable(() -> gdaxWebsocketClient.onMessage("{\"type\": \"weirdType\"}"));

        //then
        assertThat(thrown).isNull();
    }

    @Test
    public void shouldDoNothingIfNoHandlersRegistered() {
        //when
        Throwable thrown = catchThrowable(() -> gdaxWebsocketClient.onMessage(ticker_message));

        //then
        assertThat(thrown).isNull();
    }

    @Test
    public void shouldSendSubscribeMessage() throws JsonProcessingException, MessageSignatureGenerationException {
        //given
        Subscription subscription = signedSubscription();
        String subscriptionAsString = new ObjectMapper().writeValueAsString(subscription);

        Session wsSession = mock(Session.class);
        RemoteEndpoint.Async mockAsync = mock(RemoteEndpoint.Async.class);
        Future future = mock(Future.class);

        when(future.isDone()).thenReturn(true);
        when(wsSession.getAsyncRemote()).thenReturn(mockAsync);
        when(mockAsync.sendText(subscriptionAsString)).thenReturn(future);
        gdaxWebsocketClient.onOpen(wsSession);

        //when
        Future<Void> result = gdaxWebsocketClient.subscribe(subscription);

        //then
        assertThat(result).isNotNull();
        assertThat(result.isDone()).isTrue();
    }

    private Subscription signedSubscription() throws MessageSignatureGenerationException {
        Subscription subscription = new Subscription()
                .forProductIds("PRODUCT-ID-1", "PRODUCT-ID-2")
                .forChannels(ChannelName.heartbeat);
        long timestamp = Instant.now().getEpochSecond();
        String signature = MessageSignature.generate("", timestamp, TEST_SECRET);
        subscription.sign(signature, TEST_PASSPHRASE, TEST_KEY);
        return subscription;
    }

    @Test
    public void shouldReturnTrueIfSessionPresent() {
        //given
        Session wsSession = mock(Session.class);
        when(wsSession.isOpen()).thenReturn(true);
        gdaxWebsocketClient.onOpen(wsSession);

        //when
        boolean result = gdaxWebsocketClient.isConnected();

        //then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseIfSessionIsNotPresent() {
        //when
        boolean result = gdaxWebsocketClient.isConnected();

        //then
        assertThat(result).isFalse();
    }
}