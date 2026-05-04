package ca.qc.bdeb.sim.elekflow.UI.Events;

import javafx.beans.property.DoubleProperty;
import javafx.event.Event;
import javafx.event.EventType;

public class WireEvent extends Event {
    public static EventType<WireEvent> CREATE_WIRE = new EventType<>(Event.ANY, "CREATE_WIRE");

    private final DoubleProperty startXproperty;
    private final DoubleProperty startYproperty;

    public WireEvent(EventType<? extends Event> eventType, DoubleProperty startXproperty, DoubleProperty startYproperty) {
        super(eventType);

        this.startXproperty = startXproperty;
        this.startYproperty = startYproperty;
    }

    public double getStartXproperty() {
        return startXproperty.get();
    }

    public double getStartYproperty() {
        return startYproperty.get();
    }
}
