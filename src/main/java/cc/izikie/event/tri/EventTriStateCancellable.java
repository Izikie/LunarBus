package cc.izikie.event.tri;

import cc.izikie.event.Event;

public class EventTriStateCancellable extends Event {
    private final byte state;
    private boolean cancelled;

    public EventTriStateCancellable(final States state) {
        this.state = (byte) state.ordinal();
    }

    public boolean isPre() {
        return state == 0;
    }

    public boolean isOn() {
        return state == 1;
    }

    public boolean isPost() {
        return state == 2;
    }

    public final boolean isCancelled() {
        return cancelled;
    }

    public final void cancel() {
        this.cancelled = true;
    }

    public enum States {
        PRE, ON, POST
    }
}
