# Java Timetable Management System

This project is a Java-based console application designed to manage university course schedules.

## Project Overview
The application provides a command-line interface (CLI) to manage events and timetables, ensuring data integrity through specific business rules and providing persistence via CSV file handling.

### Key Features
* **Interactive Main Menu:** Navigate through options to view, add, or delete events, and manage file imports/exports.
* **Visual Timetable Grid:** Displays occurrences in a structured MON-FRI grid with time slots from 8:00 to 18:00.
* **Collision Detection:** Prevents scheduling conflicts by ensuring new events do not overlap with existing occurrences.
* **Persistence Layer:** Supports exporting and importing data using a pipe-separated CSV format.



## Technical Specification
The project follows a modular Object-Oriented Design:

### Data Model 
* **`Timetable`**: Manages the collection of events and handles logic for additions, deletions, and retrievals.
* **`Event`**: Stores metadata including shorthand (max 3 chars), full name, teacher, and event type.
* **`EventOccurrence`**: Represents a specific time slot (Weekday and Hour).
* **`Enums`**: Uses `Weekday` (MON-FRI) and `EventType` (SEMINAR, EXERCISE, PRACTICAL) to ensure type safety.

### Constraints & Logic
* **Validation**: Event shorthands are limited to 3 characters, and each event can have a maximum of 4 occurrences.
* **Formatting**: Uses tabulator characters (`\t`) for precise console alignment.
* **I/O Handling**: Utilizes `Scanner` with custom delimiters (`\n` and `|`) for robust user input and file parsing.



## How to Run

1. Ensure you have Java 17+ installed.
2. Compile the files in the src module.
3. Run TimetableCLI.java.
4. Follow the menu prompts ( 1 – 6 ) to manage your schedule.

```bash
# Example Export Format
MA2|Mathematics 2|SEMINAR|Koerkel|2|MON|8|THU|8|
