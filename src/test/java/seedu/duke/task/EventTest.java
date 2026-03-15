package seedu.duke.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.duke.calender.Calendar;
import seedu.duke.tasklist.CategoryList;

import static seedu.duke.UniTasker.handleAdd;
import static seedu.duke.UniTasker.handleDelete;
import static seedu.duke.UniTasker.handleList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventTest {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private final DateTimeFormatter formatList = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    public void addEvent_success() {
        CategoryList categoryList = new CategoryList();
        categoryList.addCategory("School");

        LocalDateTime from = LocalDateTime.parse("2026-01-01 1830", formatter);
        LocalDateTime to = LocalDateTime.parse("2026-02-02 1830", formatter);
        categoryList.addEvent(0, "consultation", from, to);

        assertEquals(1, categoryList.getCategory(0).getEventList().getSize());
        assertEquals("consultation", categoryList.getCategory(0).getEventList().get(0).getDescription());
    }

    @Test
    public void addEvent_wrongDateTimeFormat_throwException() {
        CategoryList categoryList = new CategoryList();
        categoryList.addCategory("School");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        handleAdd("add event 1 interview /from 2026/01/02 1800 /to 2026/01/02 1900".split(" "));
        assertEquals(
                "Error: Use format yyyy-MM-dd HHmm (e.g., 2026-03-11 1830)",
                outContent.toString().trim()
        );
    }

    @Test
    public void deleteEvent_success() {
        CategoryList categoryList = new CategoryList();
        categoryList.addCategory("School");

        LocalDateTime from = LocalDateTime.parse("2026-01-01 1830", formatter);
        LocalDateTime to = LocalDateTime.parse("2026-02-02 1830", formatter);
        categoryList.addEvent(0, "consultation", from, to);
        categoryList.deleteEvent(0, 0);

        assertEquals(0, categoryList.getCategory(0).getEventList().getSize());
    }

    @Test
    public void deleteEvent_invalidIndex_throwException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        CategoryList categoryList = new CategoryList();
        categoryList.addCategory("School");

        LocalDateTime from = LocalDateTime.parse("2026-01-01 1830", formatter);
        LocalDateTime to = LocalDateTime.parse("2026-02-02 1830", formatter);
        categoryList.addEvent(0, "consultation", from, to);
        handleDelete("delete event 1 0".split(" "));
        assertEquals(
                "Error: That index does not exist in the list.",
                outContent.toString().trim()
        );
    }

    @Test
    public void listEventRange_success() {
        CategoryList categoryList = new CategoryList();
        categoryList.addCategory("School");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LocalDateTime from1 = LocalDateTime.parse("2026-01-01 1830", formatter);
        LocalDateTime to1 = LocalDateTime.parse("2026-02-02 1830", formatter);

        LocalDateTime from2 = LocalDateTime.parse("2026-01-03 1830", formatter);
        LocalDateTime to2 = LocalDateTime.parse("2026-02-03 1930", formatter);

        LocalDateTime from3 = LocalDateTime.parse("2026-03-03 1830", formatter);
        LocalDateTime to3 = LocalDateTime.parse("2026-03-03 1930", formatter);

        Calendar calendar = new Calendar();
        categoryList.addEvent(0, "consultation", from1, to1);
        calendar.registerTask(categoryList.getCategory(0).getLatestEvent());

        categoryList.addEvent(0, "meeting", from2, to2);
        calendar.registerTask(categoryList.getCategory(0).getLatestEvent());

        categoryList.addEvent(0, "interview", from3, to3);
        calendar.registerTask(categoryList.getCategory(0).getLatestEvent());

        calendar.displaySpecificTypeInRange(LocalDate.parse("2026-01-01", formatList),
                LocalDate.parse("2026-02-25", formatList), Event.class);

        assertEquals(
                """
                        --- 2026-01-01 ---
                        [E][ ] consultation (from: 2026-01-01 18:30 to: 2026-02-02 18:30)
                        --- 2026-01-03 ---
                        [E][ ] meeting (from: 2026-01-03 18:30 to: 2026-02-03 19:30)
                        """,
                outContent.toString()
        );

    }

    @Test
    public void listEventRange_startDateLargerThanEndDate_throwException() {
        CategoryList categoryList = new CategoryList();
        categoryList.addCategory("School");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LocalDateTime from1 = LocalDateTime.parse("2026-01-01 1830", formatter);
        LocalDateTime to1 = LocalDateTime.parse("2026-02-02 1830", formatter);

        LocalDateTime from2 = LocalDateTime.parse("2026-01-03 1830", formatter);
        LocalDateTime to2 = LocalDateTime.parse("2026-02-03 1930", formatter);

        Calendar calendar = new Calendar();
        categoryList.addEvent(0, "consultation", from1, to1);
        calendar.registerTask(categoryList.getCategory(0).getLatestEvent());

        categoryList.addEvent(0, "meeting", from2, to2);
        calendar.registerTask(categoryList.getCategory(0).getLatestEvent());

        handleList("list range 2026-02-25 2026-01-03 /event".split(" "));

        assertEquals("Error: Start date must be earlier than End date "
                        + "(e.g., list range 2026-03-01 2026-03-07)",
                outContent.toString().trim()
        );

    }

}
