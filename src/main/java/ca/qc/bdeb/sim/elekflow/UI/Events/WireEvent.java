package ca.qc.bdeb.sim.elekflow.UI.Events;

import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.VueBorne;
import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.VueFil;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;

public class WireEvent extends Event {
    public static EventType<WireEvent> CREATE_WIRE = new EventType<>(Event.ANY, "CREATE_WIRE");
    public static EventType<WireEvent> MOUSE_MOVING = new EventType<>(Event.ANY, "MOUSE_MOVING");
    public static EventType<WireEvent> DELETE_WIRE = new EventType<>(Event.ANY, "DELETE_WIRE");
    public static EventType<WireEvent> SHOW_NODE = new EventType<>(Event.ANY, "SHOW_NODE");
    public static EventType<WireEvent> HIDE_NODE = new EventType<>(Event.ANY, "HIDE_NODE");

    private final VueFil fil;
    private final double mouseX;
    private final double mouseY;

    public WireEvent(EventType<? extends Event> eventType, VueFil fil, double mouseX, double mouseY) {
        super(eventType);
        this.fil = fil;
        this.mouseX = mouseX;
        this.mouseY = mouseY;

    }


    public VueFil getFil() {
        return fil;
    }

    public Point2D getMousePos(){
        return new Point2D(mouseX, mouseY);
    }
}
