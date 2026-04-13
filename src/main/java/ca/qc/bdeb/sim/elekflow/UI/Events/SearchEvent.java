package ca.qc.bdeb.sim.elekflow.UI.Events;

import javafx.event.Event;
import javafx.event.EventType;

public class SearchEvent extends Event {
    public static final EventType<SearchEvent> SEARCH_ENGAGED = new EventType<>(Event.ANY, "SEARCH_EVENT_ENGAGED");
    public static final EventType<SearchEvent> SEARCH_CANCELED = new EventType<>(Event.ANY, "SEARCH_EVENT_CANCELED");

    private final String searchQuerry;

    public SearchEvent(EventType<? extends Event> eventType, String searchQuerry) {
        super(eventType);
        this.searchQuerry = searchQuerry;
    }

    public String getSearchQuerry() {
        return searchQuerry;
    }
}
