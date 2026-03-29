package seedu.duke.tasklist;

import java.time.LocalDateTime;

public class EventReference {
    public final int categoryIndex;
    public final int eventIndex;

    public EventReference(int categoryIndex, int eventIndex) {
        this.categoryIndex = categoryIndex;
        this.eventIndex = eventIndex;
    }
}
