package ca.qc.bdeb.sim.elekflow.UI.Events;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventType;

public class WireEvent extends Event {
    public static EventType<WireEvent> CREATE_WIRE = new EventType<>(Event.ANY, "CREATE_WIRE");
    public static EventType<WireEvent> MOVE_END_POINT = new EventType<>(Event.ANY, "MOVE_END_POINT");

    private final ObservableValue<Number> startXproperty;
    private final ObservableValue<Number> startYproperty;

    private final double sceneX;
    private final double sceneY;

    public WireEvent(EventType<? extends Event> eventType,
                     DoubleProperty startXproperty, DoubleProperty startYproperty,
                     double sceneX, double sceneY
                     ) {
        super(eventType);

        this.startXproperty = startXproperty;
        this.startYproperty = startYproperty;

        this.sceneX = sceneX;
        this.sceneY = sceneY;
    }

    public ObservableValue<Number> getStartXproperty() {
        return startXproperty;
    }

    public ObservableValue<Number> getStartYproperty() {
        return startYproperty;
    }

    public double getSceneX(){
        return sceneX;
    }

    public double getSceneY() {
        return sceneY;
    }
}
