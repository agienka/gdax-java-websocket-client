package com.missd.gdaxjavawebsocketclient.message.channels.full;

import com.missd.gdaxjavawebsocketclient.message.channels.Side;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.FUNDS;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.ORDER_ID;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.ORDER_TYPE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.PRICE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.PRODUCT_ID;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.PROFILE_ID;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.SEQUENCE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.SIDE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.SIZE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.TIME;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.USER_ID;

/**
 * {
 *     "type": "received",
 *     "time": "2014-11-07T08:19:27.028459Z",
 *     "product_id": "BTC-USD",
 *     "sequence": 10,
 *     "order_id": "d50ec984-77a8-460a-b958-66f114b0de9b",
 *     "size": "1.34",
 *     "price": "502.1",
 *     "side": "buy",
 *     "order_type": "limit"
 * }
 * {
 *     "type": "received",
 *     "time": "2014-11-09T08:19:27.028459Z",
 *     "product_id": "BTC-USD",
 *     "sequence": 12,
 *     "order_id": "dddec984-77a8-460a-b958-66f114b0de9b",
 *     "funds": "3000.234",
 *     "side": "buy",
 *     "order_type": "market"
 * }
 */
public final class Received extends OrderUpdate {
    private final OrderType orderType;
    private final BigDecimal size;
    private final BigDecimal price;
    private final BigDecimal funds;

    public Received(Instant time, String productId, long sequence, UUID orderId, Side side, String userId, String profileId, OrderType orderType, BigDecimal size, BigDecimal price, BigDecimal funds) {
        super(time, productId, sequence, orderId, side, userId, profileId);
        this.orderType = orderType;
        this.size = size;
        this.price = price;
        this.funds = funds;
    }

    public static Received from(Map<String, Object> asMap) {
        return new Received(
                Instant.parse(asMap.get(TIME).toString()),
                asMap.get(PRODUCT_ID).toString(),
                Long.parseLong(asMap.get(SEQUENCE).toString()),
                UUID.fromString(asMap.get(ORDER_ID).toString()),
                Side.valueOf(asMap.get(SIDE).toString()),
                getStringOrNull(asMap, USER_ID),
                getStringOrNull(asMap, PROFILE_ID),
                OrderType.valueOf(asMap.get(ORDER_TYPE).toString()),
                getBigDecimalOrNull(asMap, SIZE),
                getBigDecimalOrNull(asMap, PRICE),
                getBigDecimalOrNull(asMap, FUNDS)
        );
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public BigDecimal getSize() {
        return size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getFunds() {
        return funds;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Received{");
        sb.append("orderType=").append(orderType);
        sb.append(", size=").append(size);
        sb.append(", price=").append(price);
        sb.append(", funds=").append(funds);
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

        if (!(o instanceof Received)) return false;

        Received received = (Received) o;

        return new EqualsBuilder()
                .append(orderType, received.orderType)
                .append(size, received.size)
                .append(price, received.price)
                .append(funds, received.funds)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(orderType)
                .append(size)
                .append(price)
                .append(funds)
                .toHashCode();
    }
}
