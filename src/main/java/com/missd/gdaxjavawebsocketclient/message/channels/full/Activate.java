package com.missd.gdaxjavawebsocketclient.message.channels.full;

import com.missd.gdaxjavawebsocketclient.message.channels.Side;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.FUNDS;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.ORDER_ID;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.PRIVATE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.PRODUCT_ID;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.PROFILE_ID;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.SIDE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.SIZE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.STOP_PRICE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.STOP_TYPE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.TAKER_FEE_RATE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.TIMESTAMP;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.USER_ID;

/**
 * {
 *   "type": "activate",
 *   "product_id": "test-product",
 *   "timestamp": "1483736448.299000",
 *   "user_id": "12",
 *   "profile_id": "30000727-d308-cf50-7b1c-c06deb1934fc",
 *   "order_id": "7b52009b-64fd-0a2a-49e6-d8a939753077",
 *   "stop_type": "entry",
 *   "side": "buy",
 *   "stop_price": "80",
 *   "size": "2",
 *   "funds": "50",
 *   "taker_fee_rate": "0.0025",
 *   "private": true
 * }
 */
public final class Activate {
    private final String productId;
    private final double timestamp;
    private final long userId;
    private final UUID profileId;
    private final UUID orderId;
    private final String stop_type;
    private final Side side;
    private final BigDecimal stopPrice;
    private final BigDecimal size;
    private final BigDecimal funds;
    private final BigDecimal takerFeeRate;
    private final boolean priv;

    private Activate(String productId, double timestamp, long userId, UUID profileId, UUID orderId, String stop_type, Side side, BigDecimal stopPrice, BigDecimal size, BigDecimal funds, BigDecimal takerFeeRate, boolean priv) {
        this.productId = productId;
        this.timestamp = timestamp;
        this.userId = userId;
        this.profileId = profileId;
        this.orderId = orderId;
        this.stop_type = stop_type;
        this.side = side;
        this.stopPrice = stopPrice;
        this.size = size;
        this.funds = funds;
        this.takerFeeRate = takerFeeRate;
        this.priv = priv;
    }

    public static Activate from(Map<String, Object> asMap) {
        return new Activate(
                String.valueOf(asMap.get(PRODUCT_ID)),
                Double.parseDouble(asMap.get(TIMESTAMP).toString()),
                Long.parseLong(asMap.get(USER_ID).toString()),
                UUID.fromString(asMap.get(PROFILE_ID).toString()),
                UUID.fromString(asMap.get(ORDER_ID).toString()),
                asMap.get(STOP_TYPE).toString(),
                Side.valueOf(asMap.get(SIDE).toString()),
                new BigDecimal(asMap.get(STOP_PRICE).toString()),
                new BigDecimal(asMap.get(SIZE).toString()),
                new BigDecimal(asMap.get(FUNDS).toString()),
                new BigDecimal(asMap.get(TAKER_FEE_RATE).toString()),
                Boolean.valueOf(asMap.get(PRIVATE).toString())
        );
    }

    public String getProductId() {
        return productId;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public long getUserId() {
        return userId;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getStop_type() {
        return stop_type;
    }

    public Side getSide() {
        return side;
    }

    public BigDecimal getStopPrice() {
        return stopPrice;
    }

    public BigDecimal getSize() {
        return size;
    }

    public BigDecimal getFunds() {
        return funds;
    }

    public BigDecimal getTakerFeeRate() {
        return takerFeeRate;
    }

    public boolean isPriv() {
        return priv;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Activate{");
        sb.append("productId='").append(productId).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append(", userId=").append(userId);
        sb.append(", profileId=").append(profileId);
        sb.append(", orderId=").append(orderId);
        sb.append(", stop_type='").append(stop_type).append('\'');
        sb.append(", side=").append(side);
        sb.append(", stopPrice=").append(stopPrice);
        sb.append(", size=").append(size);
        sb.append(", funds=").append(funds);
        sb.append(", takerFeeRate=").append(takerFeeRate);
        sb.append(", priv=").append(priv);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Activate)) return false;

        Activate activate = (Activate) o;

        return new EqualsBuilder()
                .append(timestamp, activate.timestamp)
                .append(userId, activate.userId)
                .append(priv, activate.priv)
                .append(productId, activate.productId)
                .append(profileId, activate.profileId)
                .append(orderId, activate.orderId)
                .append(stop_type, activate.stop_type)
                .append(side, activate.side)
                .append(stopPrice, activate.stopPrice)
                .append(size, activate.size)
                .append(funds, activate.funds)
                .append(takerFeeRate, activate.takerFeeRate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(productId)
                .append(timestamp)
                .append(userId)
                .append(profileId)
                .append(orderId)
                .append(stop_type)
                .append(side)
                .append(stopPrice)
                .append(size)
                .append(funds)
                .append(takerFeeRate)
                .append(priv)
                .toHashCode();
    }
}
