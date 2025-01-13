package cc.izikie.event;

import cc.izikie.bus.EventBusImpl;

public class EventState extends Event {
    private final byte state;

    public EventState(final byte state) {
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
}
