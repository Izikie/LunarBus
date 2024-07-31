package cc.izikie.event;

import cc.izikie.bus.EventBusImpl;

/**
 * The {@code Event} class serves as the base class for all events that can be published
 * and handled by the event bus system. It provides a common type for all events, allowing
 * them to be managed and dispatched by the {@link EventBusImpl}.
 *
 * <p>Subclasses of {@code Event} can define specific additional
 * properties and methods as needed. However, this class can also be used directly if no
 * additional properties or methods are required.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * public class MyEvent extends Event {
 *     private final String message;
 *
 *     public MyEvent(String message) {
 *         this.message = message;
 *     }
 *
 *     public String getMessage() {
 *         return message;
 *     }
 * }
 * }
 * </pre>
 *
 * @see EventBusImpl
 * @see EventCancellable
 * @see EventState
 * @see EventStateCancellable
 */
public class Event {}