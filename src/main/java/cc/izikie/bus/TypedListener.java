package cc.izikie.bus;

import cc.izikie.event.Event;

import java.lang.reflect.Type;

public record TypedListener(Type type, Listener<Event> listener) {}
