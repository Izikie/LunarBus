# LunarBus

A fast, lightweight, and dependency-free event bus system for Minecraft Coder Pack (MCP) clients.

Forked from [HomoBus](https://github.com/nevalackin/homo-bus) & [RadBus](https://github.com/nevalackin/radbus)

## Features
- **Fast**: Minimal overhead for maximum speed.
- **Dependency-free**: No external libraries required.
- **Simple**: Easy to use and integrate.
- **Lightweight**: Minimal impact on your project.
- **Priorities**: Control the order of event listeners.
- **Cancellation**: Easily cancel events.
- **Documentation**: Built-in support for event documentation.
- **State Control**: Manage events based on different states (pre, on, post).

## Installation

#### Gradle
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation group ='com.github.Izikie', name = 'LunarBus', version = 'Tag'
}
```

#### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
	    <groupId>com.github.Izikie</groupId>
	    <artifactId>LunarBus</artifactId>
	    <version>Tag</version>
	</dependency>
</dependencies>
```
or the prebuilt artifacts on the [releases page](https://github.com/Izikie/LunarBus/releases/latest).

## How to use

```java
public class Client {
    public static final Client INSTANCE = new Client();
    private final EventBusImpl bus = EventBus.newInstance()

    private final NameTagMod nameTag = new NameTagMod()

    public void init() {
        bus.subscribe(nameTag)
    }

    public void unInit() {
        bus.unsubscribe(nameTag)
    }

    public EventBusImpl getBus() {
        return bus;
    }
}
```

#### Event Classes (Event, EventCancellable, EventState. EventStateCancellable)
```java
public class Render2D {
    private final width, height

    public Render2D(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void getWidth() {
        return width;
    }

    public void getHeight() {
        return height;
    }
}
```

```java
public class NameTagMod {

    @Listen
    public final Listener<Render2D> render2DListener = event -> {
        // Code
    }
}
```

# Benchmarks
### Soon™