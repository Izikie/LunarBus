import cc.izikie.bus.EventBus;
import cc.izikie.bus.EventBusImpl;
import org.junit.jupiter.api.Test;

public final class SubUnsubBenchmarkTest {

    @Test
    public void benchmark() {
        final EventBusImpl bus = EventBus.newInstance(System.out::println);

        final long preTests = System.currentTimeMillis();

        for (int i = 1_000_000; i > 0; --i) {
            bus.subscribe(this);
            bus.unsubscribe(this);
        }

        final long postTests = System.currentTimeMillis();

        System.out.printf("1,000,000 sub/unsubs took %sms\n",
                          postTests - preTests);
    }
}