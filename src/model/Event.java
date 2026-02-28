package model;

import enums.EventType;
import enums.Weekday;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Pattern;

public class Event {
    public static final String CSV_SEPARATOR = "|";

    private final String shorthand;
    private final String name;
    private final EventType type;
    private final String teacher;
    private final List<EventOccurrence> occurrences = new ArrayList<>();

    public Event(String shorthand, String name, EventType type, String teacher) {
        this.shorthand = shorthand;
        this.name = name;
        this.type = type;
        this.teacher = teacher;
    }

    public String getShorthand() {
        return shorthand;
    }

    public List<EventOccurrence> getOccurrences() {
        return occurrences;
    }

    public boolean takesPlaceAt(EventOccurrence occurrence) {
        return occurrences.contains(occurrence);
    }

    public void addOccurrence(EventOccurrence occurrence) {
        occurrences.add(occurrence);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Shorthand: ").append(shorthand).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Type: ").append(type.name()).append("\n");
        sb.append("Teacher: ").append(teacher).append("\n");
        sb.append("Occurrences: ");
        for (int i = 0; i < occurrences.size(); i++) {
            EventOccurrence o = occurrences.get(i);
            sb.append(o.getDay().name()).append(o.getHour());
            if (i < occurrences.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }

    public void exportToFileStream(PrintStream ps) {
        ps.print(shorthand + CSV_SEPARATOR + name + CSV_SEPARATOR + type.name() + CSV_SEPARATOR + teacher + CSV_SEPARATOR + occurrences.size() + CSV_SEPARATOR);
        for (EventOccurrence o : occurrences) {
            ps.print(o.getDay().name() + CSV_SEPARATOR + o.getHour() + CSV_SEPARATOR);
        }
        ps.println();
    }

    public static Event importFromFileScanner(Scanner fileScanner) {
        Scanner lineScanner = new Scanner(fileScanner.nextLine());
        lineScanner.useDelimiter(Pattern.quote(CSV_SEPARATOR));

        if (!lineScanner.hasNext()) return null;

        String shorthand = lineScanner.next();
        String name = lineScanner.next();
        EventType type = EventType.valueOf(lineScanner.next());
        String teacher = lineScanner.next();
        int count = Integer.parseInt(lineScanner.next());

        Event event = new Event(shorthand, name, type, teacher);
        for (int i = 0; i < count; i++) {
            Weekday day = Weekday.valueOf(lineScanner.next());
            int hour = Integer.parseInt(lineScanner.next());
            event.addOccurrence(new EventOccurrence(day, hour));
        }

        return event;
    }
}
