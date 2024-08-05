package cc.izikie.event;

/**
 * The {@code EventStates} class defines constants representing the different states an event can be in.
 * These states can be used to determine the current phase of an event's lifecycle.
 *
 * <p>The available states are:</p>
 * <ul>
 *     <li>{@link #PRE} (0): Indicates the event is in the pre-processing state.</li>
 *     <li>{@link #ON} (1): Indicates the event is currently being processed.</li>
 *     <li>{@link #POST} (2): Indicates the event has completed processing.</li>
 * </ul>
 *
 * @see EventState
 * @see EventStateCancellable
 */
public final class EventStates {
    public static final byte PRE = 0;
    public static final byte ON = 1;
    public static final byte POST = 2;
}