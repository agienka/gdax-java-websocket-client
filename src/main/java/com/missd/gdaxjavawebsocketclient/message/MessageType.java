package com.missd.gdaxjavawebsocketclient.message;

import com.missd.gdaxjavawebsocketclient.message.channels.Heartbeat;
import com.missd.gdaxjavawebsocketclient.message.channels.L2Update;
import com.missd.gdaxjavawebsocketclient.message.channels.Snapshot;
import com.missd.gdaxjavawebsocketclient.message.channels.Ticker;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Activate;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Change;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Done;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Match;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Open;
import com.missd.gdaxjavawebsocketclient.message.channels.full.OrderUpdate;
import com.missd.gdaxjavawebsocketclient.message.channels.full.Received;

import java.util.Optional;

public enum MessageType {
    subscribe(Subscription.class),
    subscriptions(Subscription.class),
    unsubscribe(RevokedSubscription.class),
    ticker(Ticker.class),
    heartbeat(Heartbeat.class),
    error(ErrorMessage.class),
    snapshot(Snapshot.class),
    l2update(L2Update.class),
    match(Match.class),
    received(Received.class),
    done(Done.class),
    change(Change.class),
    activate(Activate.class),
    orderUpdate(OrderUpdate.class),
    open(Open.class);

    private Class typeClass;

    MessageType(Class typeClass) {
        this.typeClass = typeClass;
    }

    public Class getTypeClass() {
        return typeClass;
    }

    public static Optional<MessageType> valueOf(Object value) {
        try{
            return Optional.of(MessageType.valueOf(String.valueOf(value)));
        } catch (IllegalArgumentException e) { }
        return Optional.empty();
    }
}
