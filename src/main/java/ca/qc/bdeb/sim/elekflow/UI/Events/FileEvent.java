package ca.qc.bdeb.sim.elekflow.UI.Events;

import javafx.event.Event;
import javafx.event.EventType;

import java.io.File;

public class FileEvent extends Event {
    public static EventType<FileEvent> SAVE_EVENT = new EventType<>("SAVE_EVENT");

    public final File file;

    public FileEvent(EventType<? extends Event> eventType, File file) {
        super(eventType);
        this.file = file;
    }
}
