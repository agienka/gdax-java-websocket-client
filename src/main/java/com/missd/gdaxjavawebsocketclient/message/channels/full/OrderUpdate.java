package com.missd.gdaxjavawebsocketclient.message.channels.full;


import com.missd.gdaxjavawebsocketclient.message.channels.Side;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public abstract class OrderUpdate {
    protected final Instant time;
    protected final String productId;
    protected final long sequence;
    protected final UUID orderId;
    protected final Side side;
    protected final String userId;
    protected final String profileId;

    protected OrderUpdate(Instant time, String productId, long sequence, UUID orderId, Side side, String userId, String profileId) {
        this.time = time;
        this.productId = productId;
        this.sequence = sequence;
        this.orderId = orderId;
        this.side = side;
        this.userId = userId;
        this.profileId = profileId;
    }

    protected static String getStringOrNull(Map<String, Object> asMap, String key) {
        return asMap.get(key) != null ? String.valueOf(asMap.get(key)) : null;
    }
    protected static BigDecimal getBigDecimalOrNull(Map<String, Object> asMap, String key) {
        return asMap.get(key) != null ? new BigDecimal(String.valueOf(asMap.get(key))) : null;
    }

    public Instant getTime() {
        return time;
    }

    public String getProductId() {
        return productId;
    }

    public long getSequence() {
        return sequence;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Side getSide() {
        return side;
    }

    public String getUserId() {
        return userId;
    }

    public String getProfileId() {
        return profileId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderUpdate{");
        sb.append("time=").append(time);
        sb.append(", productId='").append(productId).append('\'');
        sb.append(", sequence=").append(sequence);
        sb.append(", orderId=").append(orderId);
        sb.append(", side=").append(side);
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", profileId='").append(profileId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
