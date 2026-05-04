package ca.qc.bdeb.sim.elekflow.UI.Events;

import javafx.event.Event;
import javafx.event.EventType;

public class LogEvent extends Event {
    public static EventType<LogEvent> LOG = new EventType<>(Event.ANY, "LOG");

    private final String MESSAGE;

    public LogEvent(EventType<? extends Event> eventType, String message) {
        super(eventType);
        this.MESSAGE = message;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }
}
