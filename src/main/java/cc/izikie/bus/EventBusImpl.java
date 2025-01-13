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

                // Add Subscriber (Don't Rebuild Cache)
                subscriberMap.computeIfAbsent(subscriber, key -> new ArrayList<>())
                        .add(new TypedListener(event, listener, priority));
                subscriberMap.get(subscriber).sort((a, b) -> Byte.compare(b.priority, a.priority));
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

    private static final class TypedListener {
        private final Type type;
        private final Listener<Event> listener;
        private final byte priority;

        public TypedListener(final Type type, final Listener<Event> listener, final byte priority) {
            this.type = type;
            this.listener = listener;
            this.priority = priority;
        }
    }
}
