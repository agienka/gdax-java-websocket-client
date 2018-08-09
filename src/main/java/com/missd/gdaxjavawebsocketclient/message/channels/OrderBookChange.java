package com.missd.gdaxjavawebsocketclient.message.channels;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class OrderBookChange {
    private final Side side;
    private final OrderBookItem orderBookItem;

    public OrderBookChange(Side side, OrderBookItem orderBookItem) {
        this.side = side;
        this.orderBookItem = orderBookItem;
    }

    public Side getSide() {
        return side;
    }

    public OrderBookItem getOrderBookItem() {
        return new OrderBookItem(this.orderBookItem.getPrice(), this.orderBookItem.getSize());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderBookChange{");
        sb.append("side=").append(side);
        sb.append(", orderBookItem=").append(orderBookItem);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof OrderBookChange)) return false;

        OrderBookChange that = (OrderBookChange) o;

        return new EqualsBuilder()
                .append(side, that.side)
                .append(orderBookItem, that.orderBookItem)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(side)
                .append(orderBookItem)
                .toHashCode();
    }
}
