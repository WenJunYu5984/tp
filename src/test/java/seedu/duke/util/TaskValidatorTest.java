package seedu.duke.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.exception.DuplicateCategoryException;
import seedu.duke.exception.DuplicateTaskException;
import seedu.duke.exception.HighWorkloadException;
import seedu.duke.exception.OverlapEventException;
import seedu.duke.tasklist.CategoryList;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class TaskValidatorTest {

    @BeforeEach
    public void setUp() {
        seedu.duke.UniTasker.setStartYear(2024);
        seedu.duke.UniTasker.setEndYear(2030);
        seedu.duke.UniTasker.setDailyTaskLimit(8);
    }

    @Test
    void validateNoOverlap_emptyList_doesNotThrow() {
        CategoryList empty = new CategoryList();
        LocalDateTime start = LocalDateTime.of(2026, 5, 1, 9, 0);
        LocalDateTime end = LocalDateTime.of(2026, 5, 1, 10, 0);
        assertDoesNotThrow(() -> TaskValidator.validateNoOverlap(empty, start, end));
    }

    @Test
    void validateNoOverlap_exactOverlap_throwsException() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Work");
        LocalDateTime from = LocalDateTime.of(2026, 5, 1, 9, 0);
        LocalDateTime to   = LocalDateTime.of(2026, 5, 1, 10, 0);
        categories.addEvent(0, "Standup", from, to);

        // Identical time range must conflict
        assertThrows(OverlapEventException.class, () ->
                TaskValidator.validateNoOverlap(categories, from, to));
    }

    @Test
    void validateNoOverlap_partialOverlapStart_throwsException() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Work");
        categories.addEvent(0, "Meeting",
                LocalDateTime.of(2026, 5, 1, 9, 0),
                LocalDateTime.of(2026, 5, 1, 11, 0));

        // New event starts inside existing window
        assertThrows(OverlapEventException.class, () ->
                TaskValidator.validateNoOverlap(categories,
                        LocalDateTime.of(2026, 5, 1, 10, 0),
                        LocalDateTime.of(2026, 5, 1, 12, 0)));
    }

    @Test
    void validateNoOverlap_partialOverlapEnd_throwsException() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Work");
        categories.addEvent(0, "Workshop",
                LocalDateTime.of(2026, 5, 1, 10, 0),
                LocalDateTime.of(2026, 5, 1, 12, 0));

        // New event ends inside existing window
        assertThrows(OverlapEventException.class, () ->
                TaskValidator.validateNoOverlap(categories,
                        LocalDateTime.of(2026, 5, 1, 9, 0),
                        LocalDateTime.of(2026, 5, 1, 11, 0)));
    }

    @Test
    void validateNoOverlap_newEventInsideExisting_throwsException() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Study");
        categories.addEvent(0, "Lecture",
                LocalDateTime.of(2026, 5, 1, 8, 0),
                LocalDateTime.of(2026, 5, 1, 12, 0));

        // New event fully contained within existing
        assertThrows(OverlapEventException.class, () ->
                TaskValidator.validateNoOverlap(categories,
                        LocalDateTime.of(2026, 5, 1, 9, 0),
                        LocalDateTime.of(2026, 5, 1, 11, 0)));
    }

    @Test
    void validateNoOverlap_adjacentEvents_doesNotThrow() {
        // Overlap condition: start.isBefore(existing.to) && end.isAfter(existing.from)
        // When new start == existing end, isBefore() is false — no overlap
        CategoryList categories = new CategoryList();
        categories.addCategory("Calendar");
        categories.addEvent(0, "Morning sync",
                LocalDateTime.of(2026, 5, 1, 9, 0),
                LocalDateTime.of(2026, 5, 1, 10, 0));

        assertDoesNotThrow(() ->
                TaskValidator.validateNoOverlap(categories,
                        LocalDateTime.of(2026, 5, 1, 10, 0),
                        LocalDateTime.of(2026, 5, 1, 11, 0)));
    }

    @Test
    void validateNoOverlap_differentDays_doesNotThrow() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Travel");
        categories.addEvent(0, "Flight",
                LocalDateTime.of(2026, 5, 1, 9, 0),
                LocalDateTime.of(2026, 5, 1, 11, 0));

        // Same time slot on a different day — no conflict
        assertDoesNotThrow(() ->
                TaskValidator.validateNoOverlap(categories,
                        LocalDateTime.of(2026, 5, 2, 9, 0),
                        LocalDateTime.of(2026, 5, 2, 11, 0)));
    }

    @Test
    void validateNoOverlap_conflictInSecondCategory_throwsException() {
        // validateNoOverlap iterates all categories, so an event in category B
        // must still block a conflicting new event
        CategoryList categories = new CategoryList();
        categories.addCategory("A");
        categories.addCategory("B");
        categories.addEvent(1, "Sprint review",
                LocalDateTime.of(2026, 6, 1, 14, 0),
                LocalDateTime.of(2026, 6, 1, 15, 0));

        assertThrows(OverlapEventException.class, () ->
                TaskValidator.validateNoOverlap(categories,
                        LocalDateTime.of(2026, 6, 1, 14, 30),
                        LocalDateTime.of(2026, 6, 1, 15, 30)));
    }

    // validateWorkload
    @Test
    void validateWorkload_underLimit_doesNotThrow() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Work");
        // One deadline on target date — limit is 8
        categories.addDeadline(0, "Task A", LocalDateTime.of(2026, 7, 1, 10, 0));

        assertDoesNotThrow(() ->
                TaskValidator.validateWorkload(categories, LocalDateTime.of(2026, 7, 1, 11, 0), 8));
    }

    @Test
    void validateWorkload_atLimit_throwsException() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Study");
        LocalDateTime date = LocalDateTime.of(2026, 8, 1, 10, 0);
        for (int i = 0; i < 3; i++) {
            categories.addDeadline(0, "Task " + i, date);
        }

        // totalTimedTasks (3) >= maxTasks (3) → throws
        assertThrows(HighWorkloadException.class, () ->
                TaskValidator.validateWorkload(categories, date, 3));
    }

    @Test
    void validateWorkload_emptyList_doesNotThrow() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Empty");

        assertDoesNotThrow(() ->
                TaskValidator.validateWorkload(categories,
                        LocalDateTime.of(2026, 9, 9, 9, 0), 1));
    }

    @Test
    void validateWorkload_countsAcrossMultipleCategories_throwsException() {
        CategoryList categories = new CategoryList();
        categories.addCategory("A");
        categories.addCategory("B");
        LocalDateTime date = LocalDateTime.of(2026, 10, 10, 10, 0);
        categories.addDeadline(0, "Cat A task", date);
        categories.addDeadline(1, "Cat B task", date);

        // 2 tasks across two categories >= limit of 2 → throws
        assertThrows(HighWorkloadException.class, () ->
                TaskValidator.validateWorkload(categories, date, 2));
    }

    @Test
    void validateWorkload_tasksOnDifferentDates_doesNotThrow() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Multi");
        for (int i = 1; i <= 5; i++) {
            categories.addDeadline(0, "Task " + i, LocalDateTime.of(2026, 11, i, 10, 0));
        }

        // Target date has 0 tasks; limit is 1 → no throw
        assertDoesNotThrow(() ->
                TaskValidator.validateWorkload(categories,
                        LocalDateTime.of(2026, 11, 20, 10, 0), 1));
    }

    @Test
    void validateWorkload_eventsCountTowardLimit_throwsException() {
        // validateWorkload counts both deadlines and events — verify events are included
        CategoryList categories = new CategoryList();
        categories.addCategory("Events");
        LocalDateTime date = LocalDateTime.of(2026, 8, 15, 10, 0);
        categories.addEvent(0, "Sprint planning", date,
                LocalDateTime.of(2026, 8, 15, 11, 0));

        // 1 event >= limit of 1 → throws
        assertThrows(HighWorkloadException.class, () ->
                TaskValidator.validateWorkload(categories, date, 1));
    }

    @Test
    void validateWorkload_doneTasksNotCounted_doesNotThrow() {
        // validateWorkload filters !isDone — completed tasks must not count
        CategoryList categories = new CategoryList();
        categories.addCategory("Done");
        LocalDateTime date = LocalDateTime.of(2026, 9, 1, 10, 0);
        categories.addDeadline(0, "Finished task", date);
        categories.getCategory(0).getDeadlineList().get(0).mark();

        assertDoesNotThrow(() ->
                TaskValidator.validateWorkload(categories, date, 1));
    }

    @Test
    void validateWorkload_mixOfDeadlinesAndEvents_summedTogether() {
        // One deadline + one event on the same date should both count
        CategoryList categories = new CategoryList();
        categories.addCategory("Mixed");
        LocalDateTime date = LocalDateTime.of(2026, 10, 5, 10, 0);
        categories.addDeadline(0, "Assignment", date);
        categories.addEvent(0, "Workshop", date,
                LocalDateTime.of(2026, 10, 5, 12, 0));

        // total = 2 >= limit of 2 → throws
        assertThrows(HighWorkloadException.class, () ->
                TaskValidator.validateWorkload(categories, date, 2));
    }

    // validateUniqueTask
    @Test
    void validateUniqueTask_newDescription_doesNotThrow() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Fresh");

        assertDoesNotThrow(() ->
                TaskValidator.validateUniqueTask(categories, 0, "Brand new task"));
    }

    @Test
    void validateUniqueTask_duplicateTodo_throwsException() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Todos");
        categories.addTodo(0, "Buy milk");

        assertThrows(DuplicateTaskException.class, () ->
                TaskValidator.validateUniqueTask(categories, 0, "Buy milk"));
    }

    @Test
    void validateUniqueTask_duplicateDeadline_throwsException() {
        CategoryList categories = new CategoryList();
        categories.addCategory("School");
        categories.addDeadline(0, "Submit essay", LocalDateTime.of(2026, 5, 1, 12, 0));

        assertThrows(DuplicateTaskException.class, () ->
                TaskValidator.validateUniqueTask(categories, 0, "Submit essay"));
    }

    @Test
    void validateUniqueTask_duplicateEvent_throwsException() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Work");
        categories.addEvent(0, "Sprint Review",
                LocalDateTime.of(2026, 6, 1, 9, 0),
                LocalDateTime.of(2026, 6, 1, 10, 0));

        assertThrows(DuplicateTaskException.class, () ->
                TaskValidator.validateUniqueTask(categories, 0, "Sprint Review"));
    }

    @Test
    void validateUniqueTask_leadingTrailingSpaces_treatedAsDuplicate() {
        // validateUniqueTask trims the description before checking — "  X  " matches "X"
        CategoryList categories = new CategoryList();
        categories.addCategory("Trim");
        categories.addTodo(0, "Buy milk");

        assertThrows(DuplicateTaskException.class, () ->
                TaskValidator.validateUniqueTask(categories, 0, "  Buy milk  "));
    }

    @Test
    void validateUniqueTask_sameDescriptionDifferentCategory_doesNotThrow() {
        // validateUniqueTask only checks the category at catIdx — other categories are not scanned
        CategoryList categories = new CategoryList();
        categories.addCategory("Work");
        categories.addCategory("Personal");
        categories.addTodo(0, "Buy milk");

        assertDoesNotThrow(() ->
                TaskValidator.validateUniqueTask(categories, 1, "Buy milk"));
    }

    // validateUniqueCategory
    @Test
    void validateUniqueCategory_newName_doesNotThrow() {
        CategoryList categories = new CategoryList();

        assertDoesNotThrow(() ->
                TaskValidator.validateUniqueCategory(categories, "Hobbies"));
    }

    @Test
    void validateUniqueCategory_duplicateName_throwsException() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Work");

        assertThrows(DuplicateCategoryException.class, () ->
                TaskValidator.validateUniqueCategory(categories, "Work"));
    }

    @Test
    void validateUniqueCategory_caseInsensitiveDuplicate_throwsException() {
        // validateUniqueCategory uses equalsIgnoreCase — "WORK" must match "Work"
        CategoryList categories = new CategoryList();
        categories.addCategory("Work");

        assertThrows(DuplicateCategoryException.class, () ->
                TaskValidator.validateUniqueCategory(categories, "WORK"));
    }

    @Test
    void validateUniqueCategory_emptyList_doesNotThrow() {
        CategoryList categories = new CategoryList();

        assertDoesNotThrow(() ->
                TaskValidator.validateUniqueCategory(categories, "Anything"));
    }

    // countDoneUndoneOnDate

    @Test
    void countDoneUndoneOnDate_noCategoriesAtAll_returnsZeroZero() {
        CategoryList categories = new CategoryList();

        int[] result = TaskValidator.countDoneUndoneOnDate(categories, LocalDate.of(2026, 1, 1));
        assertArrayEquals(new int[]{0, 0}, result);
    }

    @Test
    void countDoneUndoneOnDate_emptyCategory_returnsZeroZero() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Empty");

        int[] result = TaskValidator.countDoneUndoneOnDate(categories, LocalDate.of(2026, 5, 1));
        assertArrayEquals(new int[]{0, 0}, result);
    }

    @Test
    void countDoneUndoneOnDate_allUndone_correctCount() {
        CategoryList categories = new CategoryList();
        categories.addCategory("School");
        LocalDateTime date = LocalDateTime.of(2026, 6, 1, 10, 0);
        categories.addDeadline(0, "Essay", date);
        categories.addDeadline(0, "Problem set", date);

        int[] result = TaskValidator.countDoneUndoneOnDate(categories, date.toLocalDate());
        assertArrayEquals(new int[]{0, 2}, result);
    }

    @Test
    void countDoneUndoneOnDate_allDone_correctCount() {
        CategoryList categories = new CategoryList();
        categories.addCategory("School");
        LocalDateTime date = LocalDateTime.of(2026, 6, 2, 10, 0);
        categories.addDeadline(0, "Quiz A", date);
        categories.addDeadline(0, "Quiz B", date);
        categories.getCategory(0).getDeadlineList().get(0).mark();
        categories.getCategory(0).getDeadlineList().get(1).mark();

        int[] result = TaskValidator.countDoneUndoneOnDate(categories, date.toLocalDate());
        assertArrayEquals(new int[]{2, 0}, result);
    }

    @Test
    void countDoneUndoneOnDate_mixedDoneUndone_correctCount() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Work");
        LocalDateTime date = LocalDateTime.of(2026, 7, 4, 9, 0);
        categories.addDeadline(0, "Report",  date);
        categories.addDeadline(0, "Invoice", date);
        categories.addDeadline(0, "Slides",  date);
        categories.getCategory(0).getDeadlineList().get(0).mark();

        int[] result = TaskValidator.countDoneUndoneOnDate(categories, date.toLocalDate());
        assertArrayEquals(new int[]{1, 2}, result);
    }

    @Test
    void countDoneUndoneOnDate_eventsCountedCorrectly() {
        // countDoneUndoneOnDate counts events as well as deadlines
        CategoryList categories = new CategoryList();
        categories.addCategory("Calendar");
        LocalDateTime date = LocalDateTime.of(2026, 8, 10, 10, 0);
        LocalDateTime end  = LocalDateTime.of(2026, 8, 10, 11, 0);
        categories.addEvent(0, "Stand-up", date, end);
        categories.addEvent(0, "Demo",     date, end);
        categories.getCategory(0).getEventList().get(0).mark();

        int[] result = TaskValidator.countDoneUndoneOnDate(categories, date.toLocalDate());
        assertArrayEquals(new int[]{1, 1}, result);
    }

    @Test
    void countDoneUndoneOnDate_tasksOnDifferentDate_notCounted() {
        CategoryList categories = new CategoryList();
        categories.addCategory("Multi");
        categories.addDeadline(0, "Old task", LocalDateTime.of(2026, 9, 1, 10, 0));

        // Querying the next day — nothing should be counted
        int[] result = TaskValidator.countDoneUndoneOnDate(categories, LocalDate.of(2026, 9, 2));
        assertArrayEquals(new int[]{0, 0}, result);
    }

    @Test
    void countDoneUndoneOnDate_acrossMultipleCategories_aggregated() {
        CategoryList categories = new CategoryList();
        categories.addCategory("A");
        categories.addCategory("B");
        LocalDateTime date = LocalDateTime.of(2026, 10, 1, 10, 0);
        categories.addDeadline(0, "Cat A done task",   date);
        categories.addDeadline(1, "Cat B undone task", date);
        categories.getCategory(0).getDeadlineList().get(0).mark();

        int[] result = TaskValidator.countDoneUndoneOnDate(categories, date.toLocalDate());
        assertArrayEquals(new int[]{1, 1}, result);
    }
}
