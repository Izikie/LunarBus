import cc.izikie.bus.Listener;
import cc.izikie.bus.LunarBus;
import org.junit.jupiter.api.Test;

public final class PubBenchmarkTest {

    public final Listener<TestEvent> onTest = event -> {
    };

    final int iterations = 1_000_000;

    @Test
    public void benchmark() {
        LunarBus.INSTANCE.subscribe(this);

        System.gc();
        final long preMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        final long preTests = System.currentTimeMillis();

        for (int i = 0; i < iterations; i++)
            LunarBus.INSTANCE.publish(new TestEvent());

        final long postTests = System.currentTimeMillis();
        final long postMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.out.printf("%s Calls: %, d ms\n", iterations, postTests - preTests);
        System.out.printf("Memory: %, f mb\n", Helper.toMB(postMemory - preMemory));
    }
}
