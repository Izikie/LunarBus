package cc.izikie.bus;

import cc.izikie.event.Event;

@FunctionalInterface
public interface Listener<E extends Event> {
    void invoke(final E event);
}
