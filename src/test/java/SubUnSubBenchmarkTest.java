import cc.izikie.bus.LunarBus;
import org.junit.jupiter.api.Test;

public final class SubUnSubBenchmarkTest {

    final int iterations = 1_000_000;

    @Test
    public void benchmark() {
        System.gc();
        final long preMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        final long preTests = System.currentTimeMillis();

        for (int i = 0; i < iterations; i++)
            LunarBus.INSTANCE.subscribe(this);

        for (int i = 0; i < iterations; i++)
            LunarBus.INSTANCE.unsubscribe(this);

        final long postTests = System.currentTimeMillis();
        final long postMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.out.printf("%s Calls: %, d ms\n", iterations, postTests - preTests);
        System.out.printf("Memory: %, f mb\n", Helper.toMB(postMemory - preMemory));
    }
}
