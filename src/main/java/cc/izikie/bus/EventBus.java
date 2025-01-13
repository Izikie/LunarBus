package cc.izikie.bus;

import cc.izikie.event.Event;

import java.util.function.Consumer;

public sealed interface EventBus permits EventBusImpl {

    static EventBusImpl newInstance(final Consumer<String> errorLogger) {
        return new EventBusImpl(errorLogger);
    }

    void subscribe(final Object subscriber);

    void unsubscribe(final Object subscriber);

    void publish(final Event event);
}
