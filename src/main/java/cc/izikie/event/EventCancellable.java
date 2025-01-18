package cc.izikie.event;

public class EventCancellable extends Event {
    private boolean cancelled;

    public final boolean isCancelled() {
        return cancelled;
    }

    public final void cancel() {
        this.cancelled = true;
    }
}
