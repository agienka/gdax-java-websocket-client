package com.missd.gdaxjavawebsocketclient;

import com.missd.gdaxjavawebsocketclient.message.MessageType;
import com.missd.gdaxjavawebsocketclient.message.handler.GdaxErrorMessageHandler;
import com.missd.gdaxjavawebsocketclient.message.handler.MessageHandlerRegistry;

public class MessageRegistryErrorHandlerConfigurer implements GdaxWebsocketClientConfigurer {
    @Override
    public void addMessageHandlers(MessageHandlerRegistry messageHandlerRegistry) {
        messageHandlerRegistry.addMessageHandler(MessageType.error, new GdaxErrorMessageHandler());
    }
}
