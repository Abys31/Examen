package agenda;

import java.time.LocalDate;
import java.util.*;

/**
 * Description : An agenda that stores events
 */
public class Agenda {
    private ArrayList<Event> theEvents = new ArrayList<Event>();

    public Agenda() {

    }

    /**
     * Adds an event to this agenda
     *
     * @param e the event to add
     */

    public void addEvent(Event e) {
        theEvents.add(e);
    }


    public List<Event> eventsInDay(LocalDate day) {
        ArrayList<Event> eventThisDay = new ArrayList<Event>();
        for (Event e: theEvents){
            if (e.isInDay(day)) eventThisDay.add(e);
        }
        return eventThisDay;
    }
}