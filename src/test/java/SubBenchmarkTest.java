import cc.izikie.bus.EventBus;
import cc.izikie.bus.EventBusImpl;
import org.junit.jupiter.api.Test;

public final class SubBenchmarkTest {

    @Test
    public void benchmark() {
        final EventBusImpl bus = EventBus.newInstance(System.out::println);

        System.gc();
        final long preMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        final long preTests = System.currentTimeMillis();

        for (int i = 0; i < 1_000_000; i++)
            bus.subscribe(this);

        final long postTests = System.currentTimeMillis();
        final long postMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.out.printf("1,000,000 event calls took %sms\n", postTests - preTests);
        System.out.printf("Memory used: %s mb\n", Helper.toMB(postMemory - preMemory));
    }
}
