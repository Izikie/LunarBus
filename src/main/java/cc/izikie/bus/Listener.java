package cc.izikie.bus;

@FunctionalInterface
public interface Listener<Event> {
    void invoke(final Event event);
}
