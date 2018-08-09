package com.missd.gdaxjavawebsocketclient.message;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RevokedSubscription {

    private final MessageType type;
    private final List<String> productIds;
    private final List<ChannelName> channels;

    public RevokedSubscription() {
        this.type = MessageType.unsubscribe;
        this.productIds = new ArrayList<>();
        this.channels = new ArrayList<>();
    }

    public RevokedSubscription forProfuctId(String productId) {
        this.productIds.add(productId);
        return this;
    }

    public RevokedSubscription forChannels(ChannelName... channels) {
        this.channels.addAll(Arrays.asList(channels));
        return this;
    }

    public MessageType getType() {
        return type;
    }

    public List<String> getProductIds() {
        return new ArrayList<>(productIds);
    }

    public List<ChannelName> getChannels() {
        return new ArrayList<>(channels);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RevokedSubscription{");
        sb.append("type=").append(type);
        sb.append(", productIds=").append(productIds);
        sb.append(", channels=").append(channels);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof RevokedSubscription)) return false;

        RevokedSubscription that = (RevokedSubscription) o;

        return new EqualsBuilder()
                .append(type, that.type)
                .append(productIds, that.productIds)
                .append(channels, that.channels)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(type)
                .append(productIds)
                .append(channels)
                .toHashCode();
    }
}
