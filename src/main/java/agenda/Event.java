package agenda;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Event {

    /**
     * The myTitle of this event
     */
    private String myTitle;

    /**
     * The starting time of the event
     */
    private LocalDateTime myStart;

    /**
     * The duration of the event
     */
    private Duration myDuration;

    /**
     * The repetition frequency
     */
    private ChronoUnit frequency;

    /**
     * The exceptions (dates when the event does not occur)
     */
    private List<LocalDate> exceptions = new ArrayList<>();

    /**
     * Constructs an event
     *
     * @param title    the title of this event
     * @param start    the start time of this event
     * @param duration the duration of this event
     */
    public Event(String title, LocalDateTime start, Duration duration) {
        this.myTitle = title;
        this.myStart = start;
        this.myDuration = duration;
    }

    /**
     * Set the repetition frequency of the event
     *
     * @param frequency the repetition frequency
     */
    public void setRepetition(ChronoUnit frequency) {
        this.frequency = frequency;
    }

    /**
     * Add an exception date when the event does not occur
     *
     * @param date the exception date
     */
    public void addException(LocalDate date) {
        exceptions.add(date);
    }

    /**
     * Set the termination date of the event (inclusive)
     *
     * @param terminationInclusive the termination date
     */
    public void setTermination(LocalDate terminationInclusive) {
        // Implementation to calculate the termination date based on repetitions and exceptions
        LocalDate start = myStart.toLocalDate();
        LocalDate current = start;
        int count = 0;

        while (true) {
            if (!exceptions.contains(current) && (frequency == null || current.isBefore(terminationInclusive))) {
                count++;
            }
            if (frequency == null || current.isAfter(terminationInclusive)) {
                break;
            }
            current = current.plus(1, frequency);
        }

        System.out.println("Total occurrences: " + count);
    }

    /**
     * Set the termination of the event after a given number of occurrences
     *
     * @param numberOfOccurrences the number of occurrences
     */
    public void setTermination(long numberOfOccurrences) {
        LocalDate current = myStart.toLocalDate();
        for (long i = 0; i < numberOfOccurrences; i++) {
            if (!exceptions.contains(current)) {
                current = current.plus(1, frequency);
            } else {
                i--; // skip this occurrence
            }
        }
        System.out.println("Termination Date: " + current);
    }

    /**
     * Get the number of occurrences of the event until its termination date
     *
     * @return the number of occurrences
     */
    public int getNumberOfOccurrences() {
        LocalDate start = myStart.toLocalDate();
        LocalDate end = getTerminationDate();
        int count = 0;
        LocalDate current = start;

        while (!current.isAfter(end)) {
            if (!exceptions.contains(current)) {
                count++;
            }
            current = current.plus(1, frequency);
        }

        return count;
    }

    /**
     * Get the termination date of the event
     *
     * @return the termination date
     */
    public LocalDate getTerminationDate() {
        LocalDate start = myStart.toLocalDate();
        LocalDate current = start;
        while (!exceptions.contains(current) && (frequency == null || !current.isAfter(myStart.toLocalDate().plus(1, frequency)))) {
            current = current.plus(1, frequency);
        }

        return current;
    }

    /**
     * Tests if an event occurs on a given day
     *
     * @param aDay the day to test
     * @return true if the event occurs on that day, false otherwise
     */
    public boolean isInDay(LocalDate aDay) {
        LocalDate start = myStart.toLocalDate();
        LocalDateTime myEnd = myStart.plus(myDuration);
        LocalDate end = myEnd.toLocalDate();
        return start.isEqual(aDay) || end.isEqual(aDay) || (start.isBefore(aDay) && end.isAfter(aDay));
    }

    /**
     * @return the myTitle
     */
    public String getTitle() {
        return myTitle;
    }

    /**
     * @return the myStart
     */
    public LocalDateTime getStart() {
        return myStart;
    }

    /**
     * @return the myDuration
     */
    public Duration getDuration() {
        return myDuration;
    }

    @Override
    public String toString() {
        return "Event{title='%s', start=%s, duration=%s}".formatted(myTitle, myStart, myDuration);
    }
}
