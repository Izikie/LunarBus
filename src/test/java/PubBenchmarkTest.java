import cc.izikie.bus.EventBus;
import cc.izikie.bus.EventBusImpl;
import cc.izikie.bus.Listener;
import cc.izikie.bus.annotation.Listen;
import cc.izikie.bus.annotation.Priority;
import cc.izikie.event.Event;
import org.junit.jupiter.api.Test;

public final class PubBenchmarkTest {

    public static class EVENT_HIGHEST extends Event {}
    public static class EVENT_HIGH extends Event {}
    public static class EVENT extends Event {}
    public static class EVENT_LOW extends Event {}
    public static class EVENT_LOWEST extends Event {}


    @Listen(priority = Priority.VERY_HIGH)
    public final Listener<EVENT_HIGHEST> listenerHighest = event -> {
        System.out.println("Highest Event Received");
    };
    @Listen(priority = Priority.HIGH)
    public final Listener<EVENT_HIGH> listenerHigh = event -> {
        System.out.println("High Event Received");
    };
    @Listen
    public final Listener<EVENT> listener = event -> {
        System.out.println("Event Received");
    };
    @Listen(priority = Priority.LOW)
    public final Listener<EVENT_LOW> listenerLow = event -> {
        System.out.println("Low Event Received");
    };
    @Listen(priority = Priority.VERY_LOW)
    public final Listener<EVENT_LOWEST> listenerLowest = event -> {
        System.out.println("Lowest Event Received");
    };


    @Test
    public void benchmark() {
        final EventBusImpl bus = EventBus.newInstance(System.out::println);

        bus.subscribe(this);

        System.gc();
        final long preTests = System.nanoTime();
        final long preMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        bus.publish(new EVENT_HIGHEST());
        bus.publish(new EVENT_HIGH());
        bus.publish(new EVENT());
        bus.publish(new EVENT_LOW());
        bus.publish(new EVENT_LOWEST());

        final long postTests = System.nanoTime();
        final long postMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.out.printf("1,000,000 event calls took %sms\n", postTests - preTests);
        System.out.printf("Memory used: %s mb\n", Helper.toMB(postMemory - preMemory));
    }
}
