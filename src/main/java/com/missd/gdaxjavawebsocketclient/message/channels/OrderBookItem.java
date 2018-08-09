package com.missd.gdaxjavawebsocketclient.message.channels;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.List;

public final class OrderBookItem {
    private final BigDecimal price;
    private final BigDecimal size;

    public OrderBookItem(BigDecimal price, BigDecimal size) {
        this.price = price;
        this.size = size;
    }

    public static OrderBookItem from(List<?> orderBookItemAsArr) {
        return new OrderBookItem(new BigDecimal(orderBookItemAsArr.get(0).toString()), new BigDecimal(orderBookItemAsArr.get(1).toString()));
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getSize() {
        return size;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderBookItem{");
        sb.append("price=").append(price);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof OrderBookItem)) return false;

        OrderBookItem that = (OrderBookItem) o;

        return new EqualsBuilder()
                .append(price, that.price)
                .append(size, that.size)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(price)
                .append(size)
                .toHashCode();
    }
}
