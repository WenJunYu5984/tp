package seedu.duke.tasklist;

import seedu.duke.task.Event;

import java.util.Comparator;

public class EventList extends TaskList<Event>{

    public EventList() {
        super();
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < tasks.size(); i++) {
            result = result + (i + 1) + ". " + (tasks.get(i).toString()) + System.lineSeparator();
        }
        return result;
    }

    public void clearAll() {
        tasks.clear();
    }

    public void sortByDate() {
        tasks.sort(Comparator
                .comparing(Event::getFrom)
                .thenComparing(Event::getTo));
    }

    public Event getLatest() {
        if (tasks.isEmpty()) {
            return null;
        }
        return tasks.get(tasks.size() - 1);
    }
}
