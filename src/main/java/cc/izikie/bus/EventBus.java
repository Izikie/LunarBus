package cc.izikie.bus;

import cc.izikie.event.Event;

import java.util.function.Consumer;

/**
 * The EventBus interface provides methods for subscribing, unsubscribing, and publishing events.
 * It allows objects to listen for and respond to events of specific types.
 *
 * <p>Methods in this interface include:</p>
 * <ul>
 *   <li>{@link #newInstance(Consumer)}: Creates a new instance of the event bus with a specified error logger.</li>
 *   <li>{@link #subscribe(Object)}: Subscribes an object to the event bus, registering its fields annotated with {@code @Listen} as event listeners.</li>
 *   <li>{@link #unsubscribe(Object)}: Unsubscribes an object from the event bus, removing all its registered event listeners.</li>
 *   <li>{@link #publish(Event)}: Publishes an event to all registered listeners subscribed to the event's type.</li>
 * </ul>
 *
 * <p>The implementation of this interface, {@code EventBusImpl}, ensures thread safety and efficient event dispatching.</p>
 *
 * @see cc.izikie.event.Event
 * @see cc.izikie.bus.EventBusImpl
 */
public sealed interface EventBus permits EventBusImpl {

    /**
     * Creates a new instance of the event bus with the given error logger.
     *
     * @param errorLogger the error logger to be used by the event bus
     * @return a new instance of the event bus
     */
    static EventBusImpl newInstance(final Consumer<String> errorLogger) {
        return new EventBusImpl(errorLogger);
    }

    /**
     * Subscribes the given subscriber to the event bus. The subscriber's fields annotated with {@code @Listen}
     * will be registered as event listeners.
     *
     * @param subscriber the subscriber object to be subscribed
     */
    void subscribe(final Object subscriber);

    /**
     * Unsubscribes the given subscriber from the event bus. All event listeners registered by the subscriber
     * will be removed.
     *
     * @param subscriber the subscriber object to be unsubscribed
     */
    void unsubscribe(final Object subscriber);

    /**
     * Publishes the given event to all registered listeners. The event will be delivered to all listeners
     * that are subscribed to the event's type.
     *
     * @param event the event to be published
     */
    void publish(final Event event);
}