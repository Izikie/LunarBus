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
    implementation 'cc.izikie.bus/event:LunarBus:1.0.0'
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
        <groupId>cc.izikie.bus</groupId>
        <artifactId>event</artifactId>
        <version>LunarBus:1.0.0</version>
    </dependency>
</dependencies>
```
or the prebuilt artifacts on the [releases page](https://github.com/Izikie/LunarBus/releases/latest).

# Benchmarks
### Soon™
