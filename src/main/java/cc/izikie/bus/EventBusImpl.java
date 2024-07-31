package cc.izikie.bus;

import cc.izikie.bus.annotation.Listen;
import cc.izikie.event.Event;

import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * The {@code EventBusImpl} class is a concrete implementation of the {@link EventBus} interface.
 * It manages the subscription and unsubscription of event listeners and publishes events to them.
 *
 * <p>This class uses reflection to find fields annotated with {@link Listen} in subscriber objects
 * and registers them as event listeners. It maintains a cache of listeners for each event type
 * to efficiently publish events.</p>
 *
 * <p>When a subscriber is registered, all its fields annotated with {@link Listen} are inspected,
 * and the corresponding listeners are added to the cache. When an event is published, it is dispatched
 * to all listeners registered for that event type, in order of their priority.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * EventBus eventBus = EventBus.newInstance(System.out::println);
 * MyEventListener listener = new MyEventListener();
 * eventBus.subscribe(listener);
 * eventBus.publish(new MyEvent());
 * eventBus.unsubscribe(listener);
 * }
 * </pre>
 *
 * @see EventBus
 * @see Listen
 * @see Listener
 */
public final class EventBusImpl implements EventBus {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    private final Map<Type, List<Listener<Event>>> listenerCache = new HashMap<>();
    private final Map<Object, List<TypedListener>> subscriberMap = new HashMap<>();

    private final Consumer<String> logger;

    EventBusImpl(final Consumer<String> logger) {
        this.logger = logger;
    }

    @Override
    public void subscribe(final Object subscriber) {
        for (final Field field : subscriber.getClass().getDeclaredFields()) {
            final Listen annotation = field.getAnnotation(Listen.class);

            // Skip If Not Annotated
            if (annotation == null)
                continue;

            if (!field.canAccess(subscriber))
                field.trySetAccessible();

            // Store Event Type
            final Type event = ((ParameterizedType) field.getGenericType())
                    .getActualTypeArguments()[0];

            try {
                // Listener From Field
                @SuppressWarnings("unchecked")
                final Listener<Event> listener = (Listener<Event>) LOOKUP.unreflectGetter(field)
                        .invokeWithArguments(subscriber);

                final byte priority = annotation.priority();

                // Add Cache Entry
                listenerCache.computeIfAbsent(event, key -> new ArrayList<>()).add(listener);

                final List<TypedListener> list;
                final TypedListener typedListener = new TypedListener(event, listener, priority);

                // Add Subscriber (Don't Rebuild Cache)
                if (subscriberMap.containsKey(subscriber)) {
                    list = subscriberMap.get(subscriber);
                    list.add(typedListener);
                    list.sort((a, b) -> Byte.compare(b.priority, a.priority));
                } else {
                    list = new ArrayList<>();
                    list.add(typedListener);
                    subscriberMap.put(subscriber, list);
                }
            } catch (IllegalAccessException ignored) {
                // Should Never Happen
            } catch (Throwable e) {
                // Error Log Exception From Inside Event Listener
                logger.accept(String.format("ERROR: %s", e.getCause()));
            }

        }
    }

    @Override
    public void unsubscribe(final Object subscriber) {
        // Remove Subscriber
        subscriberMap.remove(subscriber);
        // Clear Cache
        listenerCache.clear();
        // Rebuild Cache From Subscribers
        // TODO: Probably A Better Way Of Doing This
        subscriberMap.values().forEach(listeners ->
                listeners.forEach(listener ->
                        listenerCache.computeIfAbsent(listener.type, key -> new ArrayList<>())
                                .add(listener.listener)));
    }

    @Override
    public void publish(final Event event) {
        final List<Listener<Event>> listeners = listenerCache.get(event.getClass());

        if (listeners == null)
            return;

        for (int i = listeners.size() - 1; i >= 0; i--)
            listeners.get(i).invoke(event);
    }

    /**
     * The {@code TypedListener} class represents a listener with a specific event type and priority.
     * It is used internally by the {@link EventBusImpl} class to manage and dispatch events to the appropriate listeners.
     *
     * <p>This class holds the type of the event, the listener itself, and the priority of the listener.
     * Listeners with higher priority levels are called before listeners with lower priority levels.</p>
     *
     * <p>Instances of this class are created when a subscriber is registered with the event bus, and they
     * are stored in the cache for efficient event dispatching.</p>
     *
     * @see EventBusImpl
     */
    private static final class TypedListener {
        private final Type type;
        private final Listener<Event> listener;
        private final byte priority;

        /**
         * Constructs a new {@code TypedListener} with the specified type, listener, and priority.
         *
         * @param type the type of the event
         * @param listener the event listener
         * @param priority the priority of the listener
         */
        public TypedListener(final Type type, final Listener<Event> listener, final byte priority) {
            this.type = type;
            this.listener = listener;
            this.priority = priority;
        }
    }
}