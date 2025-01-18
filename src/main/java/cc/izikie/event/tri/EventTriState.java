package cc.izikie.event.tri;

import cc.izikie.event.Event;

public class EventTriState extends Event {
    private final byte state;

    public EventTriState(final States state) {
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

    public enum States {
        PRE, ON, POST
    }
}
