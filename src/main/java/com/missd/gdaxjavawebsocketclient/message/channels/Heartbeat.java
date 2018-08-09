package com.missd.gdaxjavawebsocketclient.message.channels;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.Instant;
import java.util.Map;

import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.LAST_TRADE_ID;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.PRODUCT_ID;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.SEQUENCE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.TIME;

/**
 * {
 *   "type": "heartbeat",
 *   "last_trade_id": 108173,
 *   "product_id": "ETH-USD",
 *   "sequence": 3210857,
 *   "time": "2018-07-30T17:45:44.381000Z"
 *  }
 */
public final class Heartbeat {
    private final int lastTradeId;
    private final String productId;
    private final long sequence;
    private final Instant time;

    private Heartbeat(int lastTradeId, String productId, long sequence, String time) {
        this.lastTradeId = lastTradeId;
        this.productId = productId;
        this.sequence = sequence;
        this.time = Instant.parse(time);
    }

    public static Heartbeat from(Map<String, Object> heartbeatAsMap) {
        return new Heartbeat(
                Integer.parseInt(heartbeatAsMap.get(LAST_TRADE_ID).toString()),
                heartbeatAsMap.get(PRODUCT_ID).toString(),
                Long.parseLong(heartbeatAsMap.get(SEQUENCE).toString()),
                heartbeatAsMap.get(TIME).toString()
        );
    }

    public int getLastTradeId() {
        return lastTradeId;
    }

    public String getProductId() {
        return productId;
    }

    public long getSequence() {
        return sequence;
    }

    public Instant getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Heartbeat)) return false;

        Heartbeat heartbeat = (Heartbeat) o;

        return new EqualsBuilder()
                .append(lastTradeId, heartbeat.lastTradeId)
                .append(sequence, heartbeat.sequence)
                .append(productId, heartbeat.productId)
                .append(time, heartbeat.time)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(lastTradeId)
                .append(productId)
                .append(sequence)
                .append(time)
                .toHashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Heartbeat{");
        sb.append("lastTradeId=").append(lastTradeId);
        sb.append(", productId='").append(productId).append('\'');
        sb.append(", sequence=").append(sequence);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
