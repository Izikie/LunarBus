package cc.izikie.event.bi;

import cc.izikie.event.Event;

public class EventBiState extends Event {
    private final boolean pre;

    public EventBiState(final boolean pre) {
        this.pre = pre;
    }

    public final boolean isPre() {
        return pre;
    }

    public final boolean isPost() {
        return !pre;
    }
}
