package ca.qc.bdeb.sim.elekflow.UI.Events;

import javafx.event.Event;
import javafx.event.EventType;

import java.io.File;

public class ExportEvent extends Event {
    public static EventType<ExportEvent> EXPORT_SVG = new EventType<>(Event.ANY, "EXPORT_SVG");

    private final File file;

    public ExportEvent(EventType<? extends Event> eventType, File directory) {
        super(true, null, eventType);
        this.file = directory;
    }

    public File getFile() {
        return file;
    }
}
