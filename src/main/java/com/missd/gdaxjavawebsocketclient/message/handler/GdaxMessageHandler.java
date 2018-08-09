package com.missd.gdaxjavawebsocketclient.message.handler;

public interface GdaxMessageHandler<T> {
    void handleMessage(T message);
}
