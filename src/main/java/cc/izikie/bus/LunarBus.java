package cc.izikie.bus;

import cc.izikie.event.Event;

import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.*;

public enum LunarBus {
    INSTANCE;

    private final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    private final Map<Object, List<Listener<Event>>> listeners = new LinkedHashMap<>();

    public void subscribe(final Object subscriber) {
        if (listeners.containsKey(subscriber))
            return;

        final List<Listener<Event>> subscriberListeners = new LinkedList<>();

        for (final Field field : subscriber.getClass().getDeclaredFields()) {
            if (field.getType() != Listener.class)
                continue;

            try {
                if (!ensureAccessible(field))
                    continue;

                subscriberListeners.add((Listener<Event>) LOOKUP.unreflectGetter(field)
                        .invokeWithArguments(subscriber));
            } catch (final Throwable e) {
                System.err.printf("ERROR: Unexpected error with field '%s' in '%s': %s%n",
                        field.getName(), subscriber.getClass().getName(), e.getMessage());
            }
        }

        listeners.put(subscriber, subscriberListeners);
    }

    public void unsubscribe(final Object subscriber) {
        listeners.remove(subscriber);
    }

    public void publish(final Event event) {
        for (final List<Listener<Event>> listenerList : listeners.values()) {
            for (final Listener<Event> eventListener : listenerList) {
                eventListener.invoke(event);
            }
        }
    }

    public boolean ensureAccessible(final Field field) {
        final Class<?> declaringClass = field.getDeclaringClass();
        final Module declaringModule = declaringClass.getModule();
        final Module callerModule = LOOKUP.lookupClass().getModule();

        // Allow access if the module is unnamed or open to the caller's module
        if (!declaringModule.isNamed() || declaringModule.isOpen(declaringClass.getPackageName(), callerModule)) {
            field.setAccessible(true);
            return true;
        }

        // Check if the field is public and the class is public and exported
        if (Modifier.isPublic(field.getModifiers())
                && Modifier.isPublic(declaringClass.getModifiers())
                && declaringModule.isExported(declaringClass.getPackageName(), callerModule)) {
            field.setAccessible(true);
            return true;
        }

        return false;
    }
}
