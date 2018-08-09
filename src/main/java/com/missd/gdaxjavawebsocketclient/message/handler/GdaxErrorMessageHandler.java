package com.missd.gdaxjavawebsocketclient.message.handler;

import com.missd.gdaxjavawebsocketclient.message.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GdaxErrorMessageHandler implements GdaxMessageHandler<ErrorMessage> {

    private static Logger logger = LoggerFactory.getLogger(GdaxErrorMessageHandler.class);

    @Override
    public void handleMessage(ErrorMessage message) {
        logger.error("Error msg returned by server [message={}, reason={}]", message.getErrorMessage(), message.getReason());
    }
}
