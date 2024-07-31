package cc.izikie.bus.annotation;

/**
 * The {@link Priority} class defines constants representing different levels of priority.
 * These constants can be used to specify the priority of event listeners.
 *
 * <p>Priority levels include:</p>
 * <ul>
 *   <li>{@link #VERY_LOW} (0): Represents a very low priority level.</li>
 *   <li>{@link #LOW} (1): Represents a low priority level.</li>
 *   <li>{@link #MEDIUM} (2): Represents a medium priority level.</li>
 *   <li>{@link #HIGH} (3): Represents a high priority level.</li>
 *   <li>{@link #VERY_HIGH} (4): Represents a very high priority level.</li>
 * </ul>
 *
 * <p>Listeners with higher priority levels are called before listeners with lower priority levels.
 * <br/>
 * If two listeners have the same priority level, the order in which they are called is undefined.</p>
 * <p>The default priority level is {@link #MEDIUM}.</p>
 */
public final class Priority {
    public static final byte VERY_LOW = 0;
    public static final byte LOW = 1;
    public static final byte MEDIUM = 2;
    public static final byte HIGH = 3;
    public static final byte VERY_HIGH = 4;
}