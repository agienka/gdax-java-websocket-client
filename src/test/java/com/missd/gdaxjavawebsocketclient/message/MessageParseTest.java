package com.missd.gdaxjavawebsocketclient.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.missd.gdaxjavawebsocketclient.GdaxClientTest;
import com.missd.gdaxjavawebsocketclient.message.channels.Heartbeat;
import com.missd.gdaxjavawebsocketclient.message.channels.L2Update;
import com.missd.gdaxjavawebsocketclient.message.channels.Side;
import com.missd.gdaxjavawebsocketclient.message.channels.Snapshot;
import com.missd.gdaxjavawebsocketclient.message.channels.Ticker;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Activate;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Change;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Done;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Match;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Open;
import com.missd.gdaxjavawebsocketclient.message.channels.full.OrderType;
import com.missd.gdaxjavawebsocketclient.message.channels.full.OrderUpdate;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Received;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageParseTest extends GdaxClientTest {
    ObjectReader objectReader = new ObjectMapper().readerFor(new TypeReference<Map<String, Object>>() {});

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldParseSubscriptionType() throws IOException {
        //given
        Map<String, Object> subscriptionAsMap = objectReader.readValue(subscriptions);

        //when
        Subscription subscription = Subscription.from(subscriptionAsMap);

        //then
        assertThat(subscription.getChannels().get(0).getName()).isEqualTo(ChannelName.level2);
        assertThat(subscription.getChannels().get(0).getProduct_ids().get(0)).isEqualTo("ETH-USD");
        assertThat(subscription.getChannels().get(0).getProduct_ids().get(1)).isEqualTo("ETH-EUR");
        assertThat(subscription.getChannels().get(1).getName()).isEqualTo(ChannelName.heartbeat);
        assertThat(subscription.getChannels().get(1).getProduct_ids().get(0)).isEqualTo("ETH-USD");
        assertThat(subscription.getChannels().get(1).getProduct_ids().get(1)).isEqualTo("ETH-EUR");
        assertThat(subscription.getChannels().get(2).getName()).isEqualTo(ChannelName.ticker);
        assertThat(subscription.getChannels().get(2).getProduct_ids().get(0)).isEqualTo("ETH-USD");
        assertThat(subscription.getChannels().get(2).getProduct_ids().get(1)).isEqualTo("ETH-EUR");
    }

    @Test
    public void shouldParseHeartbeatChannelMessage() throws IOException {
        //given
        Map<String, Object> heartbeatAsMap = objectReader.readValue(heartbeat_message);

        //when
        Heartbeat heartbeat = Heartbeat.from(heartbeatAsMap);

        //then
        assertThat(heartbeat.getProductId()).isEqualTo("BTC-USD");
        assertThat(heartbeat.getLastTradeId()).isEqualTo(20);
        assertThat(heartbeat.getSequence()).isEqualTo(90);
        assertThat(heartbeat.getTime()).isEqualTo(Instant.parse("2014-11-07T08:19:28.464459Z"));
    }

    @Test
    public void shouldParseTickerChannelMessage() throws IOException {
        //given
        Map<String, Object> tickerAsMap = objectReader.readValue(ticker_message);

        //when
        Ticker result = Ticker.from(tickerAsMap);

        //then
        assertThat(result.getProductId()).isEqualTo("ETH-USD");
        assertThat(result.getSequence()).isEqualTo(3213383);
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("666.10000000"));
        assertThat(result.getOpen_24h()).isEqualTo(new BigDecimal("511.01000000"));
        assertThat(result.getVolume_24h()).isEqualTo(new BigDecimal("7464.43543717"));
        assertThat(result.getLow_24h()).isEqualTo(new BigDecimal("505.33000000"));
        assertThat(result.getVolume_30d()).isEqualTo(new BigDecimal("345657.51947284"));
        assertThat(result.getBest_bid()).isEqualTo(new BigDecimal("505.33"));
        assertThat(result.getBest_ask()).isEqualTo(new BigDecimal("666.1"));

    }

    @Test
    public void shouldParseErrorMessage() throws IOException {
        //given
        Map<String, Object> asMap = objectReader.readValue(error_message);

        //when
        ErrorMessage result = ErrorMessage.from(asMap);

        //then
        assertThat(result.getErrorMessage()).isEqualTo("error message");
        assertThat(result.getReason()).isEqualTo("request timestamp expired");
    }

    @Test
    public void shouldParseL2UpdateMessage() throws IOException {
        //given
        Map<String, Object> asMap = objectReader.readValue(l2update_message);

        //when
        L2Update result = L2Update.from(asMap);

        //then
        assertThat(result.getProductId()).isEqualTo("BTC-EUR");
        assertThat(result.getChanges().get(0).getSide()).isEqualTo(Side.buy);
        assertThat(result.getChanges().get(0).getOrderBookItem().getPrice()).isEqualTo(new BigDecimal("6500.09"));
        assertThat(result.getChanges().get(0).getOrderBookItem().getSize()).isEqualTo(new BigDecimal( "0.84702376"));
        assertThat(result.getChanges().get(1).getSide()).isEqualTo(Side.sell);
        assertThat(result.getChanges().get(1).getOrderBookItem().getPrice()).isEqualTo(new BigDecimal("6507.00"));
        assertThat(result.getChanges().get(1).getOrderBookItem().getSize()).isEqualTo(new BigDecimal( "1.88933140"));
        assertThat(result.getChanges().get(2).getSide()).isEqualTo(Side.sell);
        assertThat(result.getChanges().get(2).getOrderBookItem().getPrice()).isEqualTo(new BigDecimal("6504.38"));
        assertThat(result.getChanges().get(2).getOrderBookItem().getSize()).isEqualTo(BigDecimal.ZERO);

    }

    @Test
    public void shouldParseSnapshotMessage() throws IOException {
        //given
        Map<String, Object> asMap = objectReader.readValue(snapshot_message);

        //when
        Snapshot result = Snapshot.from(asMap);

        //then
        assertThat(result.getProductId()).isEqualTo("BTC-EUR");
        assertThat(result.getBids().get(0).getPrice()).isEqualTo(new BigDecimal("6500.11"));
        assertThat(result.getBids().get(0).getSize()).isEqualTo(new BigDecimal("0.45054140"));
        assertThat(result.getAsks().get(0).getPrice()).isEqualTo(new BigDecimal("6500.15"));
        assertThat(result.getAsks().get(0).getSize()).isEqualTo(new BigDecimal("0.57753524"));

    }

    @Test
    public void shouldParseMatchMessage() throws IOException {
        //given
        Map<String, Object> asMap = objectReader.readValue(match_message);

        //when
        Match result = Match.from(asMap);

        //then
        assertThat(result.getProductId()).isEqualTo("BTC-USD");
        assertThat(result.getTime()).isEqualTo(Instant.parse("2014-11-07T08:19:27.028459Z"));
        assertThat(result.getSequence()).isEqualTo(50);
        assertThat(result.getTradeId()).isEqualTo(10);
        assertThat(result.getMakerOrderId()).isEqualTo(UUID.fromString("ac928c66-ca53-498f-9c13-a110027a60e8"));
        assertThat(result.getTakerOrderId()).isEqualTo(UUID.fromString("132fb6ae-456b-4654-b4e0-d681ac05cea1"));
        assertThat(result.getSize()).isEqualTo(new BigDecimal("5.23512"));
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("400.23"));
        assertThat(result.getSide()).isEqualTo(Side.sell);
    }

    @Test
    public void shouldParseChangeMessage() throws IOException {
        //given
        Map<String, Object> asMap = objectReader.readValue(change_message);
        Map<String, Object> asMap2 = objectReader.readValue(change_message2);

        //when
        Change result = Change.from(asMap);
        Change result2 = Change.from(asMap2);

        //then
        assertOrderUpdatePart(result);
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("400.23"));
        assertThat(result.getOldSize()).isEqualTo(new BigDecimal("12.234412"));
        assertThat(result.getNewSize()).isEqualTo(new BigDecimal("5.23512"));
        assertOrderUpdatePart(result2);
        assertThat(result2.getPrice()).isEqualTo(new BigDecimal("400.23"));
        assertThat(result2.getOldfunds()).isEqualTo(new BigDecimal("12.234412"));
        assertThat(result2.getNewFunds()).isEqualTo(new BigDecimal("5.23512"));

    }

    @Test
    public void shouldParseDoneMessage() throws IOException {
        //given
        Map<String, Object> asMap = objectReader.readValue(done_message);
        Map<String, Object> asMap2 = objectReader.readValue(done_message2);

        //when
        Done result = Done.from(asMap);
        Done result2 = Done.from(asMap2);

        //then
        assertOrderUpdatePart(result);
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("502.1"));
        assertThat(result.getReason()).isEqualTo(Done.Reason.filled);
        assertThat(result.getRemainingSize()).isEqualTo(new BigDecimal(0));

        assertOrderUpdatePart(result2);
        assertThat(result2.getPrice()).isNull();
        assertThat(result2.getReason()).isEqualTo(Done.Reason.filled);
        assertThat(result2.getRemainingSize()).isEqualTo(new BigDecimal(0));
    }

    @Test
    public void shouldParseOpenMessage() throws IOException {
        //given
        Map<String, Object> asMap = objectReader.readValue(open_message);

        //when
        Open result = Open.from(asMap);

        //then
        assertOrderUpdatePart(result);
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("200.2"));
        assertThat(result.getRemainingSize()).isEqualTo(new BigDecimal("1.00"));
    }

    @Test
    public void shouldParseReceivedMessage() throws IOException {
        //given
        Map<String, Object> asMap = objectReader.readValue(received_message);
        Map<String, Object> asMap2 = objectReader.readValue(received2_message);

        //when
        Received result = Received.from(asMap);
        Received result2 = Received.from(asMap2);

        //then
        assertOrderUpdatePart(result);
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("502.1"));
        assertThat(result.getSize()).isEqualTo(new BigDecimal("1.34"));
        assertThat(result.getOrderType()).isEqualTo(OrderType.limit);
        assertThat(result.getFunds()).isNull();
        assertOrderUpdatePart(result2);
        assertThat(result2.getOrderType()).isEqualTo(OrderType.market);
        assertThat(result2.getFunds()).isEqualTo(new BigDecimal("3000.234"));
        assertThat(result2.getPrice()).isNull();
        assertThat(result2.getSize()).isNull();
    }

    @Test
    public void shouldParseActivateMessage() throws IOException {
        //given
        Map<String, Object> asMap = objectReader.readValue(activate_message);

        //when
        Activate result = Activate.from(asMap);

        //then
        assertThat(result.getProductId()).isEqualTo("test-product");
        assertThat(result.getTimestamp()).isEqualTo(1483736448.299000);
        assertThat(result.getUserId()).isEqualTo(12);
        assertThat(result.getProfileId()).isEqualTo(UUID.fromString("30000727-d308-cf50-7b1c-c06deb1934fc"));
        assertThat(result.getOrderId()).isEqualTo(UUID.fromString("7b52009b-64fd-0a2a-49e6-d8a939753077"));
        assertThat(result.getStop_type()).isEqualTo("entry");
        assertThat(result.getStopPrice()).isEqualTo(new BigDecimal("80"));
        assertThat(result.getSize()).isEqualTo(new BigDecimal("2"));
        assertThat(result.getFunds()).isEqualTo(new BigDecimal("50"));
        assertThat(result.getTakerFeeRate()).isEqualTo(new BigDecimal("0.0025"));
        assertThat(result.isPriv()).isTrue();
    }

    private void assertOrderUpdatePart(OrderUpdate result) {
        assertThat(result.getProductId()).isEqualTo("BTC-USD");
        assertThat(result.getTime()).isEqualTo(Instant.parse("2014-11-07T08:19:27.028459Z"));
        assertThat(result.getSequence()).isEqualTo(10);
        assertThat(result.getOrderId()).isEqualTo(UUID.fromString("d50ec984-77a8-460a-b958-66f114b0de9b"));
        assertThat(result.getSide()).isEqualTo(Side.sell);
    }

}