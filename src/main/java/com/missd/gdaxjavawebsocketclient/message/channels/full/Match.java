package com.missd.gdaxjavawebsocketclient.message.channels.full;

import com.missd.gdaxjavawebsocketclient.message.channels.Side;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.MAKER_ORDER_ID;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.PRICE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.PRODUCT_ID;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.SEQUENCE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.SIDE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.SIZE;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.TAKER_ORDER_ID;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.TIME;
import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.TRADE_ID;

/**
 * {
 *     "type": "match",
 *     "trade_id": 10,
 *     "sequence": 50,
 *     "maker_order_id": "ac928c66-ca53-498f-9c13-a110027a60e8",
 *     "taker_order_id": "132fb6ae-456b-4654-b4e0-d681ac05cea1",
 *     "time": "2014-11-07T08:19:27.028459Z",
 *     "product_id": "BTC-USD",
 *     "size": "5.23512",
 *     "price": "400.23",
 *     "side": "sell"
 * }
 */
public final class Match {
    private final long tradeId;
    private final long sequence;
    private final UUID makerOrderId;
    private final UUID takerOrderId;
    private final Instant time;
    private final String productId;
    private final BigDecimal size;
    private final BigDecimal price;
    private final Side side;

    private Match(long tradeId, long sequence, UUID makerOrderId, UUID takerOrderId, Instant time, String productId, BigDecimal size, BigDecimal price, Side side) {
        this.tradeId = tradeId;
        this.sequence = sequence;
        this.makerOrderId = makerOrderId;
        this.takerOrderId = takerOrderId;
        this.time = time;
        this.productId = productId;
        this.size = size;
        this.price = price;
        this.side = side;
    }

    public static Match from(Map<String, Object> matchAsMap) {
        return new Match(
                Long.parseLong(matchAsMap.get(TRADE_ID).toString()),
                Long.parseLong(matchAsMap.get(SEQUENCE).toString()),
                UUID.fromString(matchAsMap.get(MAKER_ORDER_ID).toString()),
                UUID.fromString(matchAsMap.get(TAKER_ORDER_ID).toString()),
                Instant.parse(matchAsMap.get(TIME).toString()),
                matchAsMap.get(PRODUCT_ID).toString(),
                new BigDecimal(matchAsMap.get(SIZE).toString()),
                new BigDecimal(matchAsMap.get(PRICE).toString()),
                Side.valueOf(matchAsMap.get(SIDE).toString())
        );
    }

    public long getTradeId() {
        return tradeId;
    }

    public long getSequence() {
        return sequence;
    }

    public UUID getMakerOrderId() {
        return makerOrderId;
    }

    public UUID getTakerOrderId() {
        return takerOrderId;
    }

    public Instant getTime() {
        return time;
    }

    public String getProductId() {
        return productId;
    }

    public BigDecimal getSize() {
        return size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Side getSide() {
        return side;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Match{");
        sb.append("tradeId=").append(tradeId);
        sb.append(", sequence=").append(sequence);
        sb.append(", makerOrderId=").append(makerOrderId);
        sb.append(", takerOrderId=").append(takerOrderId);
        sb.append(", time=").append(time);
        sb.append(", productId='").append(productId).append('\'');
        sb.append(", size=").append(size);
        sb.append(", price=").append(price);
        sb.append(", side=").append(side);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Match)) return false;

        Match match = (Match) o;

        return new EqualsBuilder()
                .append(tradeId, match.tradeId)
                .append(sequence, match.sequence)
                .append(makerOrderId, match.makerOrderId)
                .append(takerOrderId, match.takerOrderId)
                .append(time, match.time)
                .append(productId, match.productId)
                .append(size, match.size)
                .append(price, match.price)
                .append(side, match.side)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(tradeId)
                .append(sequence)
                .append(makerOrderId)
                .append(takerOrderId)
                .append(time)
                .append(productId)
                .append(size)
                .append(price)
                .append(side)
                .toHashCode();
    }
}
