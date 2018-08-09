package com.missd.gdaxjavawebsocketclient.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.missd.gdaxjavawebsocketclient.MessageAsMapKeys.CHANNELS;


/**
 * {
 *     "type": "subscribe",
 *     "product_ids": [
 *         "ETH-USD",
 *         "ETH-EUR"
 *     ],
 *     "channels": [
 *         "level2",
 *         "heartbeat",
 *         {
 *             "name": "ticker",
 *             "product_ids": [
 *                 "ETH-BTC",
 *                 "ETH-USD"
 *             ]
 *         }
 *     ]
 * }
 *
 * {
 *   "type": "subscriptions",
 *   "channels": [
 *     {
 *       "name": "ticker",
 *       "product_ids": [
 *         "ETH-USD"
 *       ]
 *     },
 *     {
 *       "name": "heartbeat",
 *       "product_ids": [
 *         "ETH-USD"
 *       ]
 *     }
 *   ]
 * }
 *
 */
public final class Subscription {
    private final MessageType type;
    private final List<String> product_ids;
    private final List<Channel> channels;
    private long timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL) private String signature;
    @JsonInclude(JsonInclude.Include.NON_NULL) private String passphrase;
    @JsonInclude(JsonInclude.Include.NON_NULL) private String key;

    private Subscription(MessageType type, List<String> product_ids, List<Channel> channels, long timestamp) {
        this.type = type;
        this.product_ids = product_ids;
        this.channels = channels;
        this.timestamp = timestamp;
    }

    public Subscription() {
        this(MessageType.subscribe, new ArrayList<>(), new ArrayList<>(), Instant.now().getEpochSecond());
    }

    @SuppressWarnings("unchecked")
    public static Subscription from(Map<String, Object> messageAsMap) {
        if (messageAsMap.get(CHANNELS) instanceof List) {
            List<Channel> channels = (List)((List)messageAsMap.get(CHANNELS)).stream()
                    .map(ch -> Channel.from((Map)ch))
                    .collect(Collectors.toList());
            return new Subscription(
                    MessageType.subscribe,
                    new ArrayList<>(),
                    channels,
                    Instant.now().getEpochSecond()
            );
        } else {
            return null;
        }
    }

    public void updateTimestamp(long newTimestamp) {
        this.timestamp = newTimestamp;
    }

    public ChannelsStep forProductIds(String... productIds) {
        this.product_ids.addAll(Arrays.asList(productIds));
        return this::forChannels;
    }

    private Subscription forChannels(ChannelName... channels) {
        List<Channel> channelList = Arrays.stream(channels)
                .map(chName -> new Channel(chName).addProductIds(this.product_ids))
                .collect(Collectors.toList());
        this.channels.addAll(channelList);
        return this;
    }

    public void sign(String signature, String passphrase, String apiKey) {
        this.signature = signature;
        this.passphrase = passphrase;
        this.key = apiKey;
    }

    public MessageType getType() {
        return type;
    }

    public List<String> getProduct_ids() {
        return product_ids;
    }

    public List<Channel> getChannels() {
        return new ArrayList<>(channels);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public String getKey() {
        return key;
    }

    public interface ChannelsStep {
        Subscription forChannels(ChannelName... channels);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Subscription{");
        sb.append("type=").append(type);
        sb.append(", product_ids=").append(product_ids);
        sb.append(", channels=").append(channels);
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Subscription)) return false;

        Subscription that = (Subscription) o;

        return new EqualsBuilder()
                .append(timestamp, that.timestamp)
                .append(type, that.type)
                .append(product_ids, that.product_ids)
                .append(channels, that.channels)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(type)
                .append(product_ids)
                .append(channels)
                .append(timestamp)
                .toHashCode();
    }
}
