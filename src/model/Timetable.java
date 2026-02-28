package model;

import enums.Weekday;

import java.util.*;

public class Timetable {
    private final Map<String, Event> events = new HashMap<>();

    public Collection<Event> getAllEvents() {
        return events.values();
    }

    public Event getEvent(String shorthand) {
        return events.get(shorthand);
    }

    public boolean addEvent(Event event) {
        if (events.containsKey(event.getShorthand())) return false;
        for (EventOccurrence o : event.getOccurrences()) {
            for (Event e : events.values()) {
                if (e.takesPlaceAt(o)) return false;
            }
        }
        events.put(event.getShorthand(), event);
        return true;
    }

    public boolean deleteEvent(String shorthand) {
        return events.remove(shorthand) != null;
    }

    public void clear() {
        events.clear();
    }
}
