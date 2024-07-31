package cc.izikie.event;

import cc.izikie.bus.EventBusImpl;

/**
 * The {@code EventState} class extends the {@link Event} class to add support for event states.
 * It provides methods to check the state of the event, which can be one of the constants defined in {@link EventStates}.
 *
 * <p>This class can be used for events where the state of the event is important. The state can be checked
 * using the provided methods to determine if the event is in the {@code PRE}, {@code ON}, or {@code POST} state.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * public class MyStateEvent extends EventState {
 *     public MyStateEvent(byte state) {
 *         super(state);
 *     }
 * }
 *
 * EventBus eventBus = EventBus.newInstance(System.out::println);
 * MyStateEvent event = new MyStateEvent(EventStates.PRE);
 * eventBus.publish(event);
 * if (event.isPre()) {
 *     // Handle pre-state
 * }
 * }
 * </pre>
 *
 * @see Event
 * @see EventBusImpl
 * @see EventCancellable
 * @see EventStateCancellable
 */
public class EventState extends Event {
    private final byte state;

    /**
     * Constructs a new {@link EventState} state with the specified state.
     *
     * @param state the state of the event, which should be one of the constants defined in {@link EventStates}
     */
    public EventState(final byte state) {
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
}