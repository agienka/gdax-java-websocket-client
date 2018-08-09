package com.missd.gdaxjavawebsocketclient;

import com.missd.gdaxjavawebsocketclient.message.MessageType;
import com.missd.gdaxjavawebsocketclient.message.Subscription;
import com.missd.gdaxjavawebsocketclient.message.channels.Heartbeat;
import com.missd.gdaxjavawebsocketclient.message.channels.L2Update;
import com.missd.gdaxjavawebsocketclient.message.channels.Snapshot;
import com.missd.gdaxjavawebsocketclient.message.channels.Ticker;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Activate;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Change;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Done;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Match;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Open;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Received;
import com.missd.gdaxjavawebsocketclient.message.handler.GdaxMessageHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class GdaxClientTest {
    protected static final String TEST_KEY = "cae2495b34de4a723b30baeb33c073da";
    protected static final String TEST_SECRET = "AVtDBfmrYwKknlzA5ZRWF1WyRRzRjnPk/HV8k6jbY6ZnFZcDC92Uw/gsNXVC/z4BbOe8Noe2OU/lciLJi3WhnQ==";
    protected static final String TEST_PASSPHRASE = "qohvz82gxz";
    protected static final String ticker_message = "{" +
            "\"type\": \"ticker\"," +
            "\"sequence\": 3213383," +
            "\"product_id\": \"ETH-USD\"," +
            "\"price\": \"666.10000000\"," +
            "\"open_24h\": \"511.01000000\"," +
            "\"volume_24h\": \"7464.43543717\"," +
            "\"low_24h\": \"505.33000000\"," +
            "\"high_24h\": \"666.10000000\"," +
            "\"volume_30d\": \"345657.51947284\"," +
            "\"best_bid\": \"505.33\"," +
            "\"best_ask\": \"666.1\"" +
            "}";

    protected static final String heartbeat_message = "{" +
            "\"type\": \"heartbeat\"," +
            "\"sequence\": 90," +
            "\"last_trade_id\": 20," +
            "\"product_id\": \"BTC-USD\"," +
            "\"time\": \"2014-11-07T08:19:28.464459Z\"" +
            "}";

    protected static final String error_message = "{" +
            "\"type\": \"error\"," +
            "\"message\": \"error message\"," +
            "\"reason\":\"request timestamp expired\"" +
            "}";

    protected static final String snapshot_message = "{" +
            "\"type\": \"snapshot\"," +
            "\"product_id\": \"BTC-EUR\"," +
            "\"bids\": [[\"6500.11\", \"0.45054140\"]]," +
            "\"asks\": [[\"6500.15\", \"0.57753524\"]]" +
            "}";
    protected static final String l2update_message = "{" +
            "\"type\": \"l2update\"," +
            "\"product_id\": \"BTC-EUR\"," +
            "\"changes\": [" +
            "    [\"buy\", \"6500.09\", \"0.84702376\"]," +
            "    [\"sell\", \"6507.00\", \"1.88933140\"]," +
            "    [\"sell\", \"6504.38\", \"0\"]" +
            "]" +
            "}";
    protected static final String order_update =
            "\"time\": \"2014-11-07T08:19:27.028459Z\"," +
            "\"product_id\": \"BTC-USD\"," +
            "\"sequence\": 10," +
            "\"order_id\": \"d50ec984-77a8-460a-b958-66f114b0de9b\"," +
            "\"side\": \"sell\",";
    protected static final String received_message = "{" +
            "\"type\": \"received\"," +
            order_update +
            "\"size\": \"1.34\"," +
            "\"price\": \"502.1\"," +
            "\"order_type\": \"limit\"" +
            "}";
    protected static final String received2_message = "{" +
            "\"type\": \"received\"," +
            order_update +
            "\"funds\": \"3000.234\"," +
            "\"order_type\": \"market\"" +
            "}";
    protected static final String open_message = "{" +
            "\"type\": \"open\"," +
            order_update +
            "\"price\": \"200.2\"," +
            "\"remaining_size\": \"1.00\"" +
            "}";
    protected static final String done_message = "{" +
            "\"type\": \"done\"," +
            order_update +
            "\"price\": \"502.1\"," +
            "\"reason\": \"filled\"," +
            "\"remaining_size\": \"0\"" +
            "}";
    protected static final String done_message2 = "{" +
            "\"type\": \"done\"," +
            order_update +
            "\"reason\": \"filled\"," +
            "\"remaining_size\": \"0\"" +
            "}";
    protected static final String match_message = "{" +
            "\"type\": \"match\"," +
            "\"trade_id\": 10," +
            "\"sequence\": 50," +
            "\"maker_order_id\": \"ac928c66-ca53-498f-9c13-a110027a60e8\"," +
            "\"taker_order_id\": \"132fb6ae-456b-4654-b4e0-d681ac05cea1\"," +
            "\"time\": \"2014-11-07T08:19:27.028459Z\"," +
            "\"product_id\": \"BTC-USD\"," +
            "\"size\": \"5.23512\"," +
            "\"price\": \"400.23\"," +
            "\"side\": \"sell\"" +
            "}";
    protected static final String change_message = "{" +
            "\"type\": \"change\"," +
            order_update +
            "\"new_size\": \"5.23512\"," +
            "\"old_size\": \"12.234412\"," +
            "\"price\": \"400.23\"" +
            "}";
    protected static final String change_message2 = "{" +
            "\"type\": \"change\"," +
            order_update +
            "\"new_funds\": \"5.23512\"," +
            "\"old_funds\": \"12.234412\"," +
            "\"price\": \"400.23\"" +
            "}";
    protected static final String activate_message = "{" +
            "\"type\": \"activate\"," +
            "\"product_id\": \"test-product\"," +
            "\"timestamp\": \"1483736448.299000\"," +
            "\"user_id\": \"12\"," +
            "\"profile_id\": \"30000727-d308-cf50-7b1c-c06deb1934fc\"," +
            "\"order_id\": \"7b52009b-64fd-0a2a-49e6-d8a939753077\"," +
            "\"stop_type\": \"entry\"," +
            "\"side\": \"buy\"," +
            "\"stop_price\": \"80\"," +
            "\"size\": \"2\"," +
            "\"funds\": \"50\"," +
            "\"taker_fee_rate\": \"0.0025\"," +
            "\"private\": true" +
            "}";
    protected static final String subscriptions = "{\"type\":\"subscriptions\",\"channels\":[{\"name\":\"level2\",\"product_ids\":[\"ETH-USD\",\"ETH-EUR\"]},{\"name\":\"heartbeat\",\"product_ids\":[\"ETH-USD\",\"ETH-EUR\"]},{\"name\":\"ticker\",\"product_ids\":[\"ETH-USD\",\"ETH-EUR\",\"ETH-BTC\"]}]}";

    protected static class Pair<K, V> {
        private K key;
        private V value;

        private Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        static <K, V> Pair<K, V> pair(K k, V v) {
            return new Pair<>(k, v);
        }
    }

    protected static <K, V> Map<K, V> mapOf(Pair<K, V>... pairs) {
        Map<K, V> kvMap = new HashMap<>();
        Arrays.stream(pairs).forEach(p -> kvMap.put(p.key, p.value));
        return kvMap;
    }

    protected static class HandleMessageTestParams {
        protected MessageType messageType;
        protected String message;
        protected GdaxMessageHandler messageHandler;

        public HandleMessageTestParams(MessageType messageType, String message, GdaxMessageHandler messageHandler) {
            this.messageType = messageType;
            this.message = message;
            this.messageHandler = messageHandler;
        }
    }

    protected class TestTickerMessageHandler implements GdaxMessageHandler<Ticker> {
        @Override
        public void handleMessage(Ticker message) { }
    }

    protected class TestHeartbeatMessageHandler implements GdaxMessageHandler<Heartbeat> {
        @Override
        public void handleMessage(Heartbeat message) { }
    }

    protected class TestSnapshotMessageHandler implements GdaxMessageHandler<Snapshot> {
        @Override
        public void handleMessage(Snapshot message) { }
    }

    protected class TestL2UpdateMessageHandler implements GdaxMessageHandler<L2Update> {
        @Override
        public void handleMessage(L2Update message) { }
    }

    protected class TestActivateMessageHandler implements GdaxMessageHandler<Activate> {
        @Override
        public void handleMessage(Activate message) { }
    }

    protected class TestChangeMessageHandler implements GdaxMessageHandler<Change> {
        @Override
        public void handleMessage(Change message) { }
    }

    protected class TestDoneMessageHandler implements GdaxMessageHandler<Done> {
        @Override
        public void handleMessage(Done message) { }
    }

    protected class TestMatchMessageHandler implements GdaxMessageHandler<Match> {
        @Override
        public void handleMessage(Match message) { }
    }

    protected class TestOpenMessageHandler implements GdaxMessageHandler<Open> {
        @Override
        public void handleMessage(Open message) { }
    }

    protected class TestReceivedMessageHandler implements GdaxMessageHandler<Received> {
        @Override
        public void handleMessage(Received message) { }
    }

    protected class TestSubscriptionMessageHandler implements GdaxMessageHandler<Subscription> {
        @Override
        public void handleMessage(Subscription message) { }
    }
}
