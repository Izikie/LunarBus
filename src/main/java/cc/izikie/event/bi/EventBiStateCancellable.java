package cc.izikie.event.bi;

import cc.izikie.event.Event;

public class EventBiStateCancellable extends Event {
    private final boolean pre;
    private boolean cancelled;

    public EventBiStateCancellable(final boolean pre) {
        this.pre = pre;
    }

    public final boolean isPre() {
        return pre;
    }

    public final boolean isPost() {
        return !pre;
    }

    public final boolean isCancelled() {
        return cancelled;
    }

    public final void cancel() {
        this.cancelled = true;
    }
}
