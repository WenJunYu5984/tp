package seedu.duke.calender;

import org.junit.jupiter.api.Test;

import seedu.duke.task.Deadline;
import seedu.duke.task.Event;
import seedu.duke.tasklist.CategoryList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalendarTest {

    @Test
    public void registerTask_deadlineAdded_success() {
        Calendar calendar = new Calendar();
        LocalDate targetDate = LocalDate.of(2026, 3, 15);
        LocalDateTime time = LocalDateTime.of(2026, 3, 15, 18, 30);
        Deadline deadline = new Deadline("Finish JUnit tests", time);

        calendar.registerTask(deadline);

        assertEquals(1, calendar.getTaskCountOnDate(targetDate));
    }

    @Test
    public void clear_calendarWiped_success() {
        Calendar calendar = new Calendar();
        LocalDate targetDate = LocalDate.now();
        calendar.registerTask(new Deadline("Task", LocalDateTime.now()));

        assertEquals(1, calendar.getTaskCountOnDate(targetDate));

        calendar.clear();

        assertEquals(0, calendar.getTaskCountOnDate(targetDate));
    }

    @Test
    public void refreshCalendar_includesEvents_success() {
        Calendar calendar = new Calendar();
        CategoryList categories = new CategoryList();
        categories.addCategory("Work");

        LocalDateTime from = LocalDateTime.of(2026, 6, 1, 9, 0);
        LocalDateTime to = LocalDateTime.of(2026, 6, 1, 10, 0);
        categories.addEvent(0, "Meeting", from, to);

        CategoryList.refreshCalendar(categories, calendar);
        assertEquals(1, calendar.getTaskCountOnDate(LocalDate.of(2026, 6, 1)));
    }
    @Test
    public void getTaskCountOnDate_dateWithNoTasks_returnsZero() {
        Calendar calendar = new Calendar();
        LocalDate emptyDate = LocalDate.of(2026, 12, 25);
        assertEquals(0, calendar.getTaskCountOnDate(emptyDate));
    }

    @Test
    public void registerTask_multipleTasksSameDate_incrementsCount() {
        Calendar calendar = new Calendar();
        LocalDate targetDate = LocalDate.of(2026, 5, 20);
        calendar.registerTask(new Deadline("Task 1", targetDate.atTime(10, 0)));
        calendar.registerTask(new Deadline("Task 2", targetDate.atTime(14, 0)));

        assertEquals(2, calendar.getTaskCountOnDate(targetDate));
    }
    @Test
    public void displayRange_tasksOutsideRange_notDisplayed() {
        Calendar calendar = new Calendar();
        LocalDate start = LocalDate.of(2026, 1, 1);
        LocalDate end = LocalDate.of(2026, 1, 10);

        // Task exactly on the boundary (Should display)
        calendar.registerTask(new Deadline("Inside", LocalDateTime.of(2026, 1, 5, 12, 0)));
        // Task before range (Should NOT display)
        calendar.registerTask(new Deadline("Before", LocalDateTime.of(2025, 12, 31, 23, 59)));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        calendar.displayRange(start, end);

        String output = outContent.toString();
        assert(output.contains("Inside"));
        assert(!output.contains("Before"));

        System.setOut(System.out); // Reset console output
    }

    @Test
    public void displaySpecificTypeInRange_filtersCorrectly() {
        Calendar calendar = new Calendar();
        LocalDate date = LocalDate.of(2026, 2, 2);
        LocalDateTime dateTime = date.atTime(12, 0);

        calendar.registerTask(new Deadline("I am a Deadline", dateTime));
        calendar.registerTask(new Event("I am an Event", dateTime, dateTime.plusHours(1)));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Ask specifically for Deadlines
        calendar.displaySpecificTypeInRange(date, date, Deadline.class);

        String output = outContent.toString();
        assert(output.contains("I am a Deadline"));
        assert(!output.contains("I am an Event"));

        System.setOut(System.out);
    }
}
