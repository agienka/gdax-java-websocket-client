# gdax-java-websocket-client
Simple client allowing to subscribe, authenticate, listen & consume Coinbase/Gdax websocket api.
This client was written according to the following reference documentation: [https://docs.pro.coinbase.com/#websocket-feed](https://docs.pro.coinbase.com/#websocket-feed)

## features
 * designed for **spring-boot** application in tomcat container
 * with autoconfiguration enabled (@EnableAutoConfiguration), GdaxWebsocketClient bean is available without additional config
 * supports all (documented) messages available (as of 08-2018) in the Coinbase/Gdax websocket feed
 
## installation
1. `git clone https://github.com/miss-d/gdax-java-websocket-client.git`
2. `cd ./gdax-java-websocket-client`
3. ./gradlew build
4. `cp ./build/libs/gdax-java-websocket-client-x.x.jar /{my-project}/lib`
5. add to project dependencies:
```java
dependencies {
    compile files('lib/gdax-java-websocket-client-x.x.jar')
}
```

## configuration
1. Implement `GdaxMessageHandler<T>` interface for handling message:
```java
public class HeartbeatMessageHandler implements GdaxMessageHandler<Heartbeat> {

    @Override
    public void handleMessage(Heartbeat message) {
        System.out.println("Hearbeat message arrived!"); //handle message here
    }
}
```

2. Implement `GdaxWebsocketClientConfigurer` & register message handlers

```java
@Configuration
public class GdaxClientConfig implements GdaxWebsocketClientConfigurer {

    @Override
    public void addMessageHandlers(MessageHandlerRegistry messageHandlerRegistry) {
        messageHandlerRegistry.addMessageHandler(MessageType.heartbeat, new HeartbeatMessageHandler());
        //...
    }
}
```
3. Add configuration to your `application.yml`
```properties
gdax-websocket-client:
  enabled: true # true -> this client beans are available in the context
  autoStartup: true # true -> connection is initialized right after context starts
  wsUrl: "wss://ws-feed-public.sandbox.pro.coinbase.com" # api url - see https://docs.pro.coinbase.com/#websocket-feed
  maxIdleTimeout: 1000 # max idle miliseconds before connection closes due to timeout
  maxTextMessageBufferSize: 48490 # max message size (may be too small for snapshot messages)
  auth: # generated auth data
    key: "api key"
    secret: "secret key"
    passphrase: "secret passphrase"
```

## usage
@Autowire GdaxWebsocketClient & subscribe to one or many channels [channels documentation](https://docs.pro.coinbase.com/#channels):

Supported channels can be found in ChannelName.java
```java
gdaxWebsocketClient.subscribe(new Subscription()
        .forProductIds("ETH-USD")
        .forChannels(ChannelName.heartbeat, ChannelName.full)
);
```

## errors
There is a default GdaxErrorMessageHandler registered for handling error messages from api. It just prints error level log if such message arrives. Can be overriden by implementing `GdaxMessageHandler<ErrorMessage>` and registering as shown in [Configuration](#configuration).

