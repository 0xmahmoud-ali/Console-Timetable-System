package cli;

import enums.EventType;
import enums.Weekday;
import model.*;

import java.io.*;
import java.util.*;

public class TimetableCLI {
    private final Timetable timetable = new Timetable();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new TimetableCLI().run();
    }

    public void run() {
        scanner.useDelimiter("\n");
        while (true) {
            System.out.println("""
                (1) Show current timetable
                (2) Show details of an event
                (3) Add an event
                (4) Delete an event
                (5) Export to file
                (6) Import from file
                (9) Exit""");

            switch (scanner.nextInt()) {
                case 1 -> showTimetable();
                case 2 -> showEventDetails();
                case 3 -> addEvent();
                case 4 -> deleteEvent();
                case 5 -> exportFile();
                case 6 -> importFile();
                case 9 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void showTimetable() {
        String[] hours = {"8", "10", "12", "14", "16", "18"};
        System.out.print("\t");
        for (Weekday d : Weekday.values()) System.out.print(d.name() + "\t");
        System.out.println();

        for (String hour : hours) {
            System.out.print(hour + "\t");
            for (Weekday d : Weekday.values()) {
                String cell = "";
                for (Event e : timetable.getAllEvents()) {
                    for (EventOccurrence o : e.getOccurrences()) {
                        if (o.getDay() == d && o.getHour() == Integer.parseInt(hour)) {
                            cell = e.getShorthand();
                            break;
                        }
                    }
                }
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
    }

    private void showEventDetails() {
        System.out.println("Event shorthand to show details for:");
        String shorthand = scanner.next();
        Event event = timetable.getEvent(shorthand);
        if (event == null) {
            System.out.println("Event not found.");
        } else {
            System.out.println(event);
        }
    }

    private void addEvent() {
        System.out.println("Shorthand:");
        String shorthand = scanner.next();
        if (shorthand.length() > 3 || timetable.getEvent(shorthand) != null) {
            System.out.println("Invalid or duplicate shorthand.");
            return;
        }
        System.out.println("Name:");
        String name = scanner.next();
        System.out.println("Type (SEMINAR, EXERCISE, PRACTICAL):");
        EventType type = EventType.valueOf(scanner.next());
        System.out.println("Teacher:");
        String teacher = scanner.next();
        System.out.println("Number of occurrences:");
        int count = scanner.nextInt();
        if (count < 1 || count > 4) {
            System.out.println("Too many occurrences.");
            return;
        }
        Event event = new Event(shorthand, name, type, teacher);
        for (int i = 1; i <= count; i++) {
            System.out.println("Weekday of occurrence " + i + " (e.g. MON):");
            Weekday day = Weekday.valueOf(scanner.next());
            System.out.println("Hour of occurrence " + i + " (e.g. 10):");
            int hour = scanner.nextInt();
            event.addOccurrence(new EventOccurrence(day, hour));
        }

        if (timetable.addEvent(event)) {
            System.out.println("Added event " + shorthand);
        } else {
            System.out.println("Could not add event (conflict or duplicate).");
        }
    }

    private void deleteEvent() {
        System.out.println("Shorthand of event to delete:");
        String shorthand = scanner.next();
        if (timetable.deleteEvent(shorthand)) {
            System.out.println("Deleted event " + shorthand + ".");
        } else {
            System.out.println("Event not found.");
        }
    }

    private void exportFile() {
        System.out.println("Name of file to export timetable to:");
        String filename = scanner.next();
        File file = new File(filename);
        if (file.exists()) {
            System.out.println("File already exists.");
            return;
        }

        try (PrintStream ps = new PrintStream(file)) {
            for (Event event : timetable.getAllEvents()) {
                event.exportToFileStream(ps);
            }
            System.out.println("Exported to " + filename);
        } catch (IOException e) {
            System.out.println("Export failed.");
        }
    }

    private void importFile() {
        System.out.println("Name of file to import timetable from:");
        String filename = scanner.next();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found.");
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            timetable.clear();
            while (fileScanner.hasNextLine()) {
                Event event = Event.importFromFileScanner(fileScanner);
                if (event != null) timetable.addEvent(event);
            }
            System.out.println("Imported from " + filename);
        } catch (IOException e) {
            System.out.println("Import failed.");
        }
    }
}
