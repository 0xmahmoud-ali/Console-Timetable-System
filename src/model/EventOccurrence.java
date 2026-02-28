package model;

import enums.Weekday;
import java.util.Objects;

public class EventOccurrence {
    private final Weekday day;
    private final int hour;

    public EventOccurrence(Weekday day, int hour) {
        this.day = day;
        this.hour = hour;
    }

    public Weekday getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventOccurrence that)) return false;
        return hour == that.hour && day == that.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, hour);
    }
}
