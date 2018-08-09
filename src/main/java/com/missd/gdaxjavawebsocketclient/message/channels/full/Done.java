package com.missd.gdaxjavawebsocketclient.message.channels.full;

import com.missd.gdaxjavawebsocketclient.message.channels.Side;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.ORDER_ID;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.PRICE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.PRODUCT_ID;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.PROFILE_ID;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.REASON;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.REMAINING_SIZE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.SEQUENCE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.SIDE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.TIME;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.USER_ID;

/**
 * {
 *     "type": "done",
 *     "time": "2014-11-07T08:19:27.028459Z",
 *     "product_id": "BTC-USD",
 *     "sequence": 10,
 *     "price": "200.2",
 *     "order_id": "d50ec984-77a8-460a-b958-66f114b0de9b",
 *     "reason": "filled", // or "canceled"
 *     "side": "sell",
 *     "remaining_size": "0"
 * }
 */
public final class Done extends OrderUpdate {
    private final BigDecimal price;
    private final BigDecimal remainingSize;
    private final Reason reason;


    private Done(Instant time, String productId, long sequence, UUID orderId, Side side, String userId, String profileId, BigDecimal price, BigDecimal remainingSize, Reason reason) {
        super(time, productId, sequence, orderId, side, userId, profileId);
        this.price = price;
        this.remainingSize = remainingSize;
        this.reason = reason;
    }

    public static Done from(Map<String, Object> asMap) {
        return new Done(
                Instant.parse(asMap.get(TIME).toString()),
                asMap.get(PRODUCT_ID).toString(),
                Long.parseLong(asMap.get(SEQUENCE).toString()),
                UUID.fromString(asMap.get(ORDER_ID).toString()),
                Side.valueOf(asMap.get(SIDE).toString()),
                getStringOrNull(asMap, USER_ID),
                getStringOrNull(asMap, PROFILE_ID),
                getBigDecimalOrNull(asMap, PRICE),
                getBigDecimalOrNull(asMap, REMAINING_SIZE),
                Reason.valueOf(asMap.get(REASON).toString())
        );
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getRemainingSize() {
        return remainingSize;
    }

    public Reason getReason() {
        return reason;
    }

    public enum Reason {filled, canceled}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Done{");
        sb.append("price=").append(price);
        sb.append(", remainingSize=").append(remainingSize);
        sb.append(", reason=").append(reason);
        sb.append(", time=").append(time);
        sb.append(", productId='").append(productId).append('\'');
        sb.append(", sequence=").append(sequence);
        sb.append(", orderId=").append(orderId);
        sb.append(", side=").append(side);
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", profileId='").append(profileId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Done)) return false;

        Done done = (Done) o;

        return new EqualsBuilder()
                .append(price, done.price)
                .append(remainingSize, done.remainingSize)
                .append(reason, done.reason)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(price)
                .append(remainingSize)
                .append(reason)
                .toHashCode();
    }
}
