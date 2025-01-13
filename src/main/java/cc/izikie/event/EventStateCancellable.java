package cc.izikie.event;

public class EventStateCancellable extends Event {
    private final byte state;
    private boolean cancelled;

    public EventStateCancellable(final byte state) {
        this.state = state;
    }

    public boolean isPre() {
        return state == EventStates.PRE;
    }

    public boolean isOn() {
        return state == EventStates.ON;
    }

    public boolean isPost() {
        return state == EventStates.POST;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }
}
