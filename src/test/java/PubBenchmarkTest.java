import cc.izikie.bus.EventBus;
import cc.izikie.bus.EventBusImpl;
import cc.izikie.bus.Listener;
import cc.izikie.bus.annotation.Listen;
import cc.izikie.event.Event;
import org.junit.jupiter.api.Test;

public final class PubBenchmarkTest {

    private static final Event PLACEHOLDER = new Event();

    @Listen
    public final Listener<Event> listener = event -> {

    };

    @Test
    public void benchmark() {
        final EventBusImpl bus = EventBus.newInstance(System.out::println);

        bus.subscribe(this);

        final long preTests = System.currentTimeMillis();

        for (int i = 1_000_000; i > 0; --i) {
            bus.publish(PLACEHOLDER);
        }

        final long postTests = System.currentTimeMillis();

        System.out.printf("1,000,000 event calls took %sms\n",
                          postTests - preTests);
    }
}