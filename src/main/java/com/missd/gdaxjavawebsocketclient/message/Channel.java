package com.missd.gdaxjavawebsocketclient.message;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Channel {
    public static final String NAME = "name";
    public static final String PRODUCT_IDS = "product_ids";
    private final ChannelName name;
    private final List<String> product_ids;

    public Channel(ChannelName channelName) {
        this.name = channelName;
        this.product_ids = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public static Channel from(Map<String, Object> channelAsMap) {
        return new Channel(ChannelName.valueOf(String.valueOf(channelAsMap.get(NAME))))
                .addProductIds((List)channelAsMap.get(PRODUCT_IDS));
    }

    public Channel addProductIds(List<String> channelIds) {
        this.product_ids.addAll(channelIds);
        return this;
    }

    public ChannelName getName() {
        return name;
    }

    public List<String> getProduct_ids() {
        return new ArrayList<>(product_ids);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Channel{");
        sb.append("name=").append(name);
        sb.append(", product_ids=").append(product_ids);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Channel)) return false;

        Channel channel = (Channel) o;

        return new EqualsBuilder()
                .append(name, channel.name)
                .append(product_ids, channel.product_ids)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(product_ids)
                .toHashCode();
    }
}
