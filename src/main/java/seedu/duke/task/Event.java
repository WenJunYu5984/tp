package seedu.duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task implements Timed {

    protected LocalDateTime from;
    protected LocalDateTime to;
    protected boolean isRecurring;
    protected int recurringGroupId;

    public Event(String description, LocalDateTime from, LocalDateTime to, boolean isRecurring, int recurringGroupId) {
        super(description);
        this.from = from;
        this.to = to;
        this.isRecurring = isRecurring;
        this.recurringGroupId = recurringGroupId;
    }

    public LocalDateTime getFrom(){
        return from;
    }

    public LocalDateTime getTo(){
        return to;
    }

    public int getRecurringGroupId(){
        return recurringGroupId;
    }

    public boolean getIsRecurring(){
        return isRecurring;
    }

    public String toString() {
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        String fromFormatted = from.format(displayFormatter);
        String toFormatted = to.format(displayFormatter);
        return (isRecurring ? "[RE]" + "[Group " + recurringGroupId + "]" : "[E]") + super.toString()
                + " (from: " + fromFormatted + " to: " + toFormatted + ")";
    }

    public String toStringRecurring() {
        DateTimeFormatter displayFormatterTime = DateTimeFormatter.ofPattern("EEEE HHmm");
        String fromFormatted = from.format(displayFormatterTime);
        String toFormatted = to.format(displayFormatterTime);

        return "[RE][Group " + recurringGroupId + "]" + super.toString()
                + " (from: " + fromFormatted + " to: " + toFormatted + ")";
    }

    @Override
    public String toFileFormat() {
        DateTimeFormatter storageFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        String fromFormatted = from.format(storageFormatter);
        String toFormatted = to.format(storageFormatter);
        if (isRecurring) {
            return String.format("RE | %d | %s | %s | %s | %d", (isDone ? 1 : 0), description,
                    fromFormatted, toFormatted,recurringGroupId);
        }
        return String.format("E | %d | %s | %s | %s", (isDone ? 1 : 0), description,
                fromFormatted, toFormatted);
    }

    @Override
    public LocalDateTime getDate() {
        return from;
    }
}
