package com.missd.gdaxjavawebsocketclient;

import com.missd.gdaxjavawebsocketclient.message.handler.MessageHandlerRegistry;

public interface GdaxWebsocketClientConfigurer {
    void addMessageHandlers(MessageHandlerRegistry messageHandlerRegistry);
}
