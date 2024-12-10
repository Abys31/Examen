package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * A repetitive Event
 */
public class Repetition extends Event {
    private ChronoUnit frequency;
    private ArrayList<LocalDate> exceptions = new ArrayList<LocalDate>();

    public Repetition(String title, LocalDateTime start, Duration duration, ChronoUnit frequency) {
        super(title, start, duration);
        this.frequency = frequency;
    }

    @Override
    public boolean isInDay(LocalDate aDay) {
        if (exceptions.contains(aDay))
            return false;
        LocalDate dateTest = LocalDate.from(getStart());
        while (dateTest.isBefore(aDay) || dateTest.equals(aDay)) {
            if (dateTest.plus(1, frequency).equals(aDay)) {
                return true;
            }
            dateTest = dateTest.plus(1, frequency);
        }
        return super.isInDay(aDay);
    }

    public void addException(LocalDate date) {
        exceptions.add(date);
    }

    public ChronoUnit getFrequency() {
        return frequency;
    }

    public void setRepetition(ChronoUnit frequency) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
