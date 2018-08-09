package com.missd.gdaxjavawebsocketclient;

import com.missd.gdaxjavawebsocketclient.message.handler.MessageHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.standard.AnnotatedEndpointConnectionManager;

import javax.websocket.WebSocketContainer;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "gdax-websocket-client")
@ConditionalOnProperty("gdax.enabled")
public class GdaxWebSocketConfiguration {

    private String wsUrl;
    private int maxIdleTimeout;
    private int maxTextMessageBufferSize;
    private boolean autoStartup;
    private AuthAttributes auth;
    private final List<GdaxWebsocketClientConfigurer> configurers = new ArrayList<>();

    @Autowired(required = false)
    public void setConfigurers(List<GdaxWebsocketClientConfigurer> configurers) {
        if (!configurers.isEmpty()) {
            this.configurers.addAll(configurers);
        }
    }

    @Bean
    public AnnotatedEndpointConnectionManager connectionManager(GdaxWebsocketClient gdaxWebsocketClient) {
        AnnotatedEndpointConnectionManager connectionManager = new AnnotatedEndpointConnectionManager(gdaxWebsocketClient, wsUrl);
        WebSocketContainer container = connectionManager.getWebSocketContainer();
        container.setDefaultMaxSessionIdleTimeout(maxIdleTimeout);
        container.setDefaultMaxTextMessageBufferSize(maxTextMessageBufferSize);
        connectionManager.setAutoStartup(autoStartup);
        return connectionManager;
    }

    @Bean
    GdaxWebsocketClient websocketClient(MessageHandlerRegistry messageHandlerRegistry) {
        return new GdaxWebsocketClient(messageHandlerRegistry.getMessageHandlers(), auth, Clock.systemUTC());
    }

    @Bean
    GdaxWebsocketClientConfigurer messageRegistryConfigurerError() {
        return new MessageRegistryErrorHandlerConfigurer();
    }

    @Bean
    MessageHandlerRegistry messageHandlerRegistry() {
        MessageHandlerRegistry messageHandlerRegistry = new MessageHandlerRegistry();
        this.configurers.forEach(c -> c.addMessageHandlers(messageHandlerRegistry));
        return messageHandlerRegistry;
    }

    public String getWsUrl() {
        return wsUrl;
    }

    public void setWsUrl(String wsUrl) {
        this.wsUrl = wsUrl;
    }

    public int getMaxIdleTimeout() {
        return maxIdleTimeout;
    }

    public void setMaxIdleTimeout(int maxIdleTimeout) {
        this.maxIdleTimeout = maxIdleTimeout;
    }

    public int getMaxTextMessageBufferSize() {
        return maxTextMessageBufferSize;
    }

    public void setMaxTextMessageBufferSize(int maxTextMessageBufferSize) {
        this.maxTextMessageBufferSize = maxTextMessageBufferSize;
    }

    public boolean isAutoStartup() {
        return autoStartup;
    }

    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    public AuthAttributes getAuth() {
        return auth;
    }

    public GdaxWebSocketConfiguration setAuth(AuthAttributes auth) {
        this.auth = auth;
        return this;
    }

}
