package com.missd.gdaxjavawebsocketclient.message.channels;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.PRODUCT_ID;


/**
 * {
 *     "type": "snapshot",
 *     "product_id": "BTC-EUR",
 *     "bids": [["6500.11", "0.45054140"]],
 *     "asks": [["6500.15", "0.57753524"]]
 * }
 */
public final class Snapshot {
    private final String productId;
    private final List<OrderBookItem> bids = new ArrayList<>();
    private final List<OrderBookItem> asks = new ArrayList<>();

    private Snapshot(String productId) {
        this.productId = productId;
    }

    @SuppressWarnings("unchecked")
    public static Snapshot from(Map<String, Object> snapshotAsMap) {
        Snapshot snapshot = new Snapshot(String.valueOf(snapshotAsMap.get(PRODUCT_ID)));
        List<List<?>> bids = (List)snapshotAsMap.get("bids");
        bids.forEach(b -> snapshot.bids.add(OrderBookItem.from(b)));
        List<List<?>> asks = (List)snapshotAsMap.get("asks");
        asks.forEach(a -> snapshot.asks.add(OrderBookItem.from(a)));
        return snapshot;
    }

    public String getProductId() {
        return productId;
    }

    public List<OrderBookItem> getBids() {
        return new ArrayList<>(bids);
    }

    public List<OrderBookItem> getAsks() {
        return new ArrayList<>(asks);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Snapshot{");
        sb.append("productId='").append(productId).append('\'');
        sb.append(", bids=").append(bids);
        sb.append(", asks=").append(asks);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Snapshot)) return false;

        Snapshot snapshot = (Snapshot) o;

        return new EqualsBuilder()
                .append(productId, snapshot.productId)
                .append(bids, snapshot.bids)
                .append(asks, snapshot.asks)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(productId)
                .append(bids)
                .append(asks)
                .toHashCode();
    }
}
