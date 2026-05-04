package ca.qc.bdeb.sim.elekflow.UI.Events;

import javafx.event.Event;
import javafx.event.EventType;

public class ZoomEvent extends Event {
    public static EventType<ZoomEvent> ZOOM = new EventType<>(Event.ANY, "ZOOM");

    private double mouseX;
    private double mouseY;
    private double zoom;

    public ZoomEvent(EventType<? extends Event> eventType, double mouseX, double mouseY, double zoom) {
        super(eventType);
    }
}
