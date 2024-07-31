package cc.izikie.event;

import cc.izikie.bus.EventBusImpl;

/**
 * The {@code EventCancellable} class extends the {@link Event} class to add support for event cancellation.
 * It provides methods to check if the event has been cancelled and to cancel the event.
 *
 * <p>This class can be used for events where the ability to cancel the event is required. When an event
 * is cancelled, it can be handled appropriately by the event listeners.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * public class MyCancellableEvent extends EventCancellable {
 *     // Additional properties and methods
 * }
 *
 * EventBus eventBus = EventBus.newInstance(System.out::println);
 * MyCancellableEvent event = new MyCancellableEvent();
 * eventBus.publish(event);
 * if (event.isCancelled()) {
 *     // Handle cancellation
 * }
 * }
 * </pre>
 *
 * @see Event
 * @see EventBusImpl
 * @see EventState
 * @see EventStateCancellable
 */
public class EventCancellable extends Event {
    private boolean cancelled;

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