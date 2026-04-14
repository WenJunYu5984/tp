package seedu.duke.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.exception.IllegalDateException;
import seedu.duke.tasklist.CategoryList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DeadlineTest {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");

    @BeforeEach
    public void setUp() {
        seedu.duke.UniTasker.setStartYear(2024);
        seedu.duke.UniTasker.setEndYear(2030);
        seedu.duke.UniTasker.setDailyTaskLimit(8);
    }

    // Construction and field accessors
    @Test
    public void constructor_validInputs_descriptionStoredCorrectly() {
        Deadline d = new Deadline("Submit report", LocalDateTime.of(2026, 6, 15, 23, 59));

        assertEquals("Submit report", d.getDescription());
    }

    @Test
    public void constructor_validInputs_byDateStoredCorrectly() {
        LocalDateTime by = LocalDateTime.of(2026, 6, 15, 23, 59);
        Deadline d = new Deadline("Submit report", by);

        assertEquals(by, d.getBy());
    }

    @Test
    public void constructor_newDeadline_isNotDoneByDefault() {
        Deadline d = new Deadline("Buy tickets", LocalDateTime.of(2026, 7, 1, 10, 0));

        assertFalse(d.getIsDone());
    }

    // mark / unmark
    @Test
    public void mark_setsIsDoneToTrue() {
        Deadline d = new Deadline("Submit report", LocalDateTime.of(2026, 6, 15, 23, 59));
        d.mark();

        assertTrue(d.getIsDone());
    }

    @Test
    public void unmark_afterMark_setsIsDoneBackToFalse() {
        Deadline d = new Deadline("Submit report", LocalDateTime.of(2026, 6, 15, 23, 59));
        d.mark();
        d.unmark();

        assertFalse(d.getIsDone());
    }

    @Test
    public void setDeadlineStatus_toTrue_marksAsDone() {
        CategoryList list = new CategoryList();
        list.addCategory("Check");
        list.addDeadline(0, "Mark me", LocalDateTime.of(2026, 4, 4, 8, 0));
        list.setDeadlineStatus(0, 0, true);

        assertTrue(list.getCategory(0).getDeadlineList().get(0).getIsDone());
    }

    @Test
    public void setDeadlineStatus_toFalse_unmarksTask() {
        CategoryList list = new CategoryList();
        list.addCategory("Uncheck");
        list.addDeadline(0, "Unmark me", LocalDateTime.of(2026, 4, 4, 8, 0));
        list.setDeadlineStatus(0, 0, true);
        list.setDeadlineStatus(0, 0, false);

        assertFalse(list.getCategory(0).getDeadlineList().get(0).getIsDone());
    }

    // getDate — Timed interface
    @Test
    public void getDate_returnsSameAsGetBy() {
        LocalDateTime time = LocalDateTime.of(2026, 9, 9, 9, 0);
        Deadline d = new Deadline("Same date", time);

        assertEquals(d.getBy(), d.getDate());
    }

    // toString
    @Test
    public void toString_notDone_containsUncheckedMark() {
        Deadline d = new Deadline("Write report", LocalDateTime.of(2026, 5, 5, 12, 0));

        assertTrue(d.toString().startsWith("[D]"), "Should start with [D] type marker");
        assertTrue(d.toString().contains("[ ]"), "Undone task should show empty checkbox");
    }

    @Test
    public void toString_done_containsCheckedMark() {
        Deadline d = new Deadline("Write report", LocalDateTime.of(2026, 5, 5, 12, 0));
        d.mark();

        assertTrue(d.toString().contains("[X]"), "Done task should show X in checkbox");
    }

    @Test
    public void toString_containsFormattedDate() {
        Deadline d = new Deadline("Year-end task", LocalDateTime.of(2026, 12, 31, 23, 59));

        assertTrue(d.toString().contains("31-12-2026 2359"));
    }

    @Test
    public void toString_midnightTime_formattedCorrectly() {
        Deadline d = new Deadline("Midnight task", LocalDateTime.of(2026, 1, 31, 0, 0));

        assertEquals("[D][ ] Midnight task (by: 31-01-2026 0000)", d.toString());
    }

    // toFileFormat
    @Test
    public void toFileFormat_notDone_correctOutput() {
        Deadline d = new Deadline("Read book", LocalDateTime.of(2026, 12, 1, 18, 0));

        assertEquals("D | 0 | Read book | 01-12-2026 1800", d.toFileFormat());
    }

    @Test
    public void toFileFormat_done_statusIsOne() {
        Deadline d = new Deadline("Submit form", LocalDateTime.of(2026, 6, 1, 9, 0));
        d.mark();

        assertTrue(d.toFileFormat().contains("D | 1"),
                "Done deadline must serialise status as 1");
    }

    @Test
    public void toFileFormat_notDone_statusIsZero() {
        Deadline d = new Deadline("Submit form", LocalDateTime.of(2026, 6, 1, 9, 0));

        assertTrue(d.toFileFormat().contains("D | 0"),
                "Undone deadline must serialise status as 0");
    }

    @Test
    public void toFileFormat_dateWithLeadingZeros_formattedCorrectly() {
        // Day 5, month 7 — both should be zero-padded to "05" and "07"
        Deadline d = new Deadline("Padded date task", LocalDateTime.of(2026, 7, 5, 8, 5));

        assertEquals("D | 0 | Padded date task | 05-07-2026 0805", d.toFileFormat());
    }

    // parseDateTime
    @Test
    public void parseDateTime_validInput_returnsCorrectDateTime() throws IllegalDateException {
        LocalDateTime result = Deadline.parseDateTime("15-06-2026 1430");

        assertEquals(LocalDateTime.of(2026, 6, 15, 14, 30), result);
    }

    @Test
    public void parseDateTime_midnightTime_parsedCorrectly() throws IllegalDateException {
        LocalDateTime result = Deadline.parseDateTime("05-05-2026 0000");

        assertEquals(0, result.getHour());
        assertEquals(0, result.getMinute());
        assertEquals(2026, result.getYear());
    }

    @Test
    public void parseDateTime_endOfYearDate_parsedCorrectly() throws IllegalDateException {
        LocalDateTime result = Deadline.parseDateTime("31-12-2026 2359");

        assertEquals(31, result.getDayOfMonth());
        assertEquals(12, result.getMonthValue());
        assertEquals(2026, result.getYear());
    }

    @Test
    public void parseDateTime_dateOnly_defaultsToLastMinute() throws IllegalDateException {
        LocalDateTime result = Deadline.parseDateTime("05-05-2026");

        assertEquals(23, result.getHour());
        assertEquals(59, result.getMinute());
        assertEquals(2026, result.getYear());
    }

    @Test
    public void parseDateTime_invalidFormat_throwsIllegalDateException() {
        assertThrows(IllegalDateException.class, () ->
                Deadline.parseDateTime("2025-12-31 2359"));
    }

    @Test
    public void parseDateTime_completelyInvalidString_throwsIllegalDateException() {
        assertThrows(IllegalDateException.class, () ->
                Deadline.parseDateTime("not a date"));
    }

    // CategoryList integration — add / delete / sort / clear
    @Test
    public void addDeadline_success() {
        CategoryList list = new CategoryList();
        list.addCategory("School");
        list.addDeadline(0, "submit project",
                LocalDateTime.parse("12-03-2026 1830", formatter));

        assertEquals(1, list.getCategory(0).getDeadlineList().getSize());
        assertEquals("submit project",
                list.getCategory(0).getDeadlineList().get(0).getDescription());
    }

    @Test
    public void deleteDeadline_removesCorrectEntry() {
        CategoryList list = new CategoryList();
        list.addCategory("Test");
        LocalDateTime t = LocalDateTime.of(2026, 3, 1, 10, 0);
        list.addDeadline(0, "First", t);
        list.addDeadline(0, "Second", t);
        list.deleteDeadline(0, 0);

        assertEquals(1, list.getCategory(0).getDeadlineList().getSize());
        assertEquals("Second", list.getCategory(0).getDeadlineList().get(0).getDescription());
    }

    @Test
    public void sortDeadlines_reverseOrder_correctlySorted() {
        CategoryList list = new CategoryList();
        list.addCategory("School");
        list.addDeadline(0, "Late Task",
                LocalDateTime.parse("20-03-2026 1000", formatter));
        list.addDeadline(0, "Early Task",
                LocalDateTime.parse("10-03-2026 1000", formatter));
        list.sortDeadlines(0);

        assertEquals("Early Task",
                list.getCategory(0).getDeadlineList().get(0).getDescription());
    }

    @Test
    public void deleteAllDeadlines_clearsCategory() {
        CategoryList list = new CategoryList();
        list.addCategory("Personal");
        LocalDateTime t = LocalDateTime.parse("12-03-2026 1200", formatter);
        list.addDeadline(0, "task 1", t);
        list.addDeadline(0, "task 2", t);
        list.deleteAllDeadlines(0);

        assertEquals(0, list.getCategory(0).getDeadlineList().getSize());
    }
}
