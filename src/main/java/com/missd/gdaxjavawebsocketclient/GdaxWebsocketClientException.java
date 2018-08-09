package com.missd.gdaxjavawebsocketclient;

public class GdaxWebsocketClientException extends RuntimeException {

    public GdaxWebsocketClientException(String info, Object gdaxMessage, Throwable cause) {
        super(String.format(info + " [message=%s]", gdaxMessage), cause);
    }

    public GdaxWebsocketClientException(String info, Object gdaxMessage) {
        super(String.format(info + " [message=%s]", gdaxMessage));
    }
}
