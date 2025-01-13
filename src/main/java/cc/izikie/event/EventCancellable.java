package cc.izikie.event;

public class EventCancellable extends Event {
    private boolean cancelled;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void cancel() {
        this.cancelled = false;
    }
}
