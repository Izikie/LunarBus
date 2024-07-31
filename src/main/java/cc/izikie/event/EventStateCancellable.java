package cc.izikie.event;

import cc.izikie.bus.EventBusImpl;

/**
 * The {@code EventStateCancellable} class extends the {@link Event} class to add support for both event states and cancellation.
 * It provides methods to check the state of the event and to check if the event has been cancelled or to cancel the event.
 *
 * <p>This class can be used for events where both the state and the ability to cancel the event are important. The state can be checked
 * using the provided methods to determine if the event is in the {@code PRE}, {@code ON}, or {@code POST} state, and the event can be
 * cancelled if necessary.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * public class MyStateCancellableEvent extends EventStateCancellable {
 *     public MyStateCancellableEvent(byte state) {
 *         super(state);
 *     }
 * }
 *
 * EventBus eventBus = EventBus.newInstance(System.out::println);
 * MyStateCancellableEvent event = new MyStateCancellableEvent(EventStates.PRE);
 * eventBus.publish(event);
 * if (event.isCancelled()) return;
 * if (event.isPre()) {
 *     // Handle pre-state
 * }
 * if (event.isCancelled()) {
 *     // Handle cancellation
 * }
 * }
 * </pre>
 *
 * @see Event
 * @see EventBusImpl
 * @see EventCancellable
 * @see EventState
 */
public class EventStateCancellable extends Event {
    private final byte state;
    private boolean cancelled;

    /**
     * Constructs a new {@link EventStateCancellable} with the specified state.
     *
     * @param state the state of the event, which should be one of the constants defined in {@link EventStates}
     */
    public EventStateCancellable(final byte state) {
        this.state = state;
    }

    /**
     * Checks if the event state is PRE.
     *
     * @return true if the event state is {@code PRE}, false otherwise
     */
    public boolean isPre() {
        return state == EventStates.PRE;
    }

    /**
     * Checks if the event state is ON.
     *
     * @return true if the event state is {@code ON}, false otherwise
     */
    public boolean isOn() {
        return state == EventStates.ON;
    }

    /**
     * Checks if the event state is POST.
     *
     * @return true if the event state is {@code POST}, false otherwise
     */
    public boolean isPost() {
        return state == EventStates.POST;
    }

    /**
     * Checks if the event has been cancelled.
     *
     * @return true if the event is cancelled, false otherwise
     */
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Cancels the event.
     */
    public void cancel() {
        this.cancelled = false;
    }
}