package cc.izikie.bus;

/**
 * The {@link Listener} interface represents a functional interface for handling events.
 * Implementations of this interface define the action to be taken when an event is invoked.
 *
 * <p>This interface is a functional interface and can be used as the assignment target for a lambda expression or method reference.</p>
 *
 * @param <Event> the type of event to be handled
 */
@FunctionalInterface
public interface Listener<Event> {
    /**
     * Invokes the listener with the given event.
     *
     * @param event the event to be handled
     */
    void invoke(final Event event);
}