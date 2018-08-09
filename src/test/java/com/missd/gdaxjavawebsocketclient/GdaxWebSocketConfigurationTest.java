package com.missd.gdaxjavawebsocketclient;

import com.missd.gdaxjavawebsocketclient.message.MessageType;
import com.missd.gdaxjavawebsocketclient.message.handler.GdaxMessageHandler;
import com.missd.gdaxjavawebsocketclient.message.handler.MessageHandlerRegistry;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.missd.gdaxjavawebsocketclient.GdaxClientTest.Pair.pair;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class GdaxWebSocketConfigurationTest extends GdaxClientTest{
    private GdaxWebSocketConfiguration gdaxWebSocketConfiguration;

    @Before
    public void setUp() throws Exception {
        gdaxWebSocketConfiguration = new GdaxWebSocketConfiguration();
    }

    @Test
    public void shouldConfigureMessageRegistryForClient() {
        //given
        MessageType key1 = MessageType.ticker;
        MessageType key2 = MessageType.heartbeat;
        GdaxMessageHandler messageHandler1 = mock(TestHeartbeatMessageHandler.class);
        GdaxMessageHandler messageHandler2 = mock(TestTickerMessageHandler.class);
        List<GdaxWebsocketClientConfigurer> configurerList = Arrays.asList(new MyConfigurer(mapOf(
                pair(key1, messageHandler1),
                pair(key2, messageHandler2)
        )));
        gdaxWebSocketConfiguration.setConfigurers(configurerList);

        //when
        MessageHandlerRegistry messageHandlerRegistry = gdaxWebSocketConfiguration.messageHandlerRegistry();

        //then
        assertThat(messageHandlerRegistry.getMessageHandlers().get(key1)).isEqualTo(messageHandler1);
        assertThat(messageHandlerRegistry.getMessageHandlers().get(key2)).isEqualTo(messageHandler2);
    }

    private class MyConfigurer implements GdaxWebsocketClientConfigurer {

        private final Map<MessageType, GdaxMessageHandler> messageHandlerMap;

        private MyConfigurer(Map<MessageType, GdaxMessageHandler> messageHandlerMap) {
            this.messageHandlerMap = messageHandlerMap;
        }

        @Override
        public void addMessageHandlers(MessageHandlerRegistry messageHandlerRegistry) {
            messageHandlerMap.forEach(messageHandlerRegistry::addMessageHandler);
        }
    }

}