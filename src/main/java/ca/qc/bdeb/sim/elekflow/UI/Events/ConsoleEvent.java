package ca.qc.bdeb.sim.elekflow.UI.Events;

import javafx.event.Event;
import javafx.event.EventType;

public class ConsoleEvent extends Event {
    public static EventType<ConsoleEvent> OPEN_CONSOLE = new EventType<>(Event.ANY, "OPEN_CONSOLE");
    public static EventType<ConsoleEvent> HIDE_CONSOLE = new EventType<>(Event.ANY, "HIDE_CONSOLE");

    public ConsoleEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
