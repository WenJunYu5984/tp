package seedu.duke.tasklist;

import org.junit.jupiter.api.Test;

import seedu.duke.task.Deadline;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineListTest {

    @Test
    public void addAndGetLatest_success() {
        DeadlineList list = new DeadlineList();
        LocalDateTime time = LocalDateTime.of(2026, 3, 15, 10, 0);

        list.add(new Deadline("Task 1", time));
        assertEquals("Task 1", list.getLatest().getDescription());

        list.add(new Deadline("Task 2", time));
        assertEquals("Task 2", list.getLatest().getDescription());
    }

    @Test
    public void delete_updatesSize_success() {
        DeadlineList list = new DeadlineList();
        list.add(new Deadline("Task", LocalDateTime.now()));
        assertEquals(1, list.getSize());

        list.delete(0);
        assertEquals(0, list.getSize());
    }

    @Test
    public void delete_shiftsRemainingTasks_success() {
        DeadlineList list = new DeadlineList();
        list.add(new Deadline("First", LocalDateTime.of(2026, 1, 1, 10, 0)));
        list.add(new Deadline("Second", LocalDateTime.of(2026, 1, 1, 12, 0)));

        list.delete(0); // Remove "First"

        // "Second" should now be at index 0
        assertEquals(1, list.getSize());
        assertEquals("Second", list.get(0).getDescription());
    }

    @Test
    public void getLatest_afterSort_returnsChronologicallyLatest() {
        DeadlineList list = new DeadlineList();
        LocalDateTime early = LocalDateTime.of(2026, 1, 1, 10, 0);
        LocalDateTime late = LocalDateTime.of(2026, 12, 31, 23, 59);

        // Add late task first, then early task
        list.add(new Deadline("Late Task", late));
        list.add(new Deadline("Early Task", early));

        list.sortByDate();

        // After sorting, the late task should be at the end
        assertEquals("Late Task", list.getLatest().getDescription());
    }
}
