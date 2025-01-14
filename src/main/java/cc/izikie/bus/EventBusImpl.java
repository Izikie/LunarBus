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

                // Add Subscriber Entry
                subscriberMap.computeIfAbsent(subscriber, key -> new ArrayList<>())
                        .add(new TypedListener(event, listener, priority));
                subscriberMap.get(subscriber).sort((a, b) -> Byte.compare(b.priority, a.priority));
            } catch (IllegalAccessException ignored) {
                // Should Never Happen
            } catch (Throwable e) {
                // Error Log Exception
                logger.accept(String.format("ERROR: %s", e));
            }
        }
    }

    @Override
    public void unsubscribe(final Object subscriber) {
        // Remove Subscriber
        final List<TypedListener> listeners = subscriberMap.remove(subscriber);
        if (listeners == null)
            return;

        // Remove Listeners From Cache
        for (final TypedListener listener : listeners) {
            final List<Listener<Event>> eventListeners = listenerCache.get(listener.type);
            if (eventListeners == null)
                continue;

            eventListeners.remove(listener.listener);

            if (eventListeners.isEmpty())
                listenerCache.remove(listener.type);
        }
    }

    @Override
    public void publish(final Event event) {
        final List<Listener<Event>> listeners = listenerCache.get(event.getClass());

        if (listeners == null)
            return;

        for (int i = 0; i < listeners.size(); i++)
            listeners.get(i).invoke(event);
    }

    private record TypedListener(Type type, Listener<Event> listener, byte priority) {}
}
