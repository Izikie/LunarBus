package cc.izikie.bus.annotation;

import cc.izikie.bus.Listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@link Listen} annotation is used to mark fields as event listeners within a class.
 * Fields annotated with {@link Listen} will be registered to listen for events when the class is subscribed to an event bus.
 *
 * <p>The {@code priority} element specifies the priority level of the listener. Listeners with higher priority levels
 * are called before listeners with lower priority levels. If two listeners have the same priority level, the order
 * in which they are called is undefined.</p>
 *
 * <p>If the {@code priority} element is not specified, the default priority level is {@link Priority#MEDIUM}.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * public class MyEventListener {
 *     @Listen(priority = Priority.HIGH)
 *     private final Listener<MyEvent> myEventListener = event -> {
 *         // handle event
 *     };
 * }
 * }
 * </pre>
 *
 * @see Priority
 * @see Listener
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Listen {
    /**
     * Specifies the priority level of the listener. Listeners with higher priority levels
     * are called before listeners with lower priority levels. If two listeners have the same
     * priority level, the order in which they are called is undefined.
     *
     * <p>If the {@code priority} element is not specified, the default priority level is {@link Priority#MEDIUM}.</p>
     *
     * @return the priority level of the listener, ranging from {@link Priority#VERY_LOW} to {@link Priority#VERY_HIGH}
     */
    byte priority() default Priority.MEDIUM;
}