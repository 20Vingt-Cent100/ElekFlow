package ca.qc.bdeb.sim.elekflow.UI.Events;

import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.VueFil;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class WireEvent extends Event {
    public static EventType<WireEvent> CREATE_WIRE = new EventType<>(Event.ANY, "CREATE_WIRE");
    public static EventType<WireEvent> MOUSE_MOVING = new EventType<>(Event.ANY, "MOUSE_MOVING");
    public static EventType<WireEvent> DELETE_WIRE = new EventType<>(Event.ANY, "DELETE_WIRE");
    public static EventType<WireEvent> SHOW_NODE = new EventType<>(Event.ANY, "SHOW_NODE");
    public static EventType<WireEvent> HIDE_NODE = new EventType<>(Event.ANY, "HIDE_NODE");

    private final VueFil fil;
    private final MouseEvent event;

    public WireEvent(EventType<? extends Event> eventType, VueFil fil, MouseEvent event) {
        super(eventType);
        this.fil = fil;
        this.event = event;

    }


    public VueFil getFil() {
        return fil;
    }

    public MouseEvent getEvent() {
        return event;
    }
}
