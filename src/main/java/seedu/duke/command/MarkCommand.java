package seedu.duke.command;

import seedu.duke.appcontainer.AppContainer;
import seedu.duke.ui.ErrorUi;
import seedu.duke.ui.TaskUi;

import java.util.ArrayList;

public class MarkCommand implements Command {
    public static final int MARK_MIN_LENGTH = 4;

    public static final int INDEX_OF_MARK_TYPE = 1;
    public static final int INDEX_OF_FIRST_TASK_TO_MARK = 3;

    private final String[] sentence;
    private final boolean isMark;

    public MarkCommand(String[] sentence, boolean isMark) {
        this.sentence = sentence;
        this.isMark = isMark;
    }

    @Override
    public void execute(AppContainer container) {
        if (sentence.length < MARK_MIN_LENGTH) {
            ErrorUi.printUnknownCommand("mark/unmark", "todo, deadline or event");
            return;
        }

        try {
            String secondCommand = sentence[INDEX_OF_MARK_TYPE];
            switch (secondCommand) {
            case "todo":
                handleTodo(container);
                break;
            case "deadline":
                handleDeadline(container);
                break;
            case "event":
                handleEvent(container);
                break;
            default:
                ErrorUi.printUnknownCommand("mark/unmark", "todo, deadline or event");
                break;
            }
            CommandSupport.saveData(container);
        } catch (Exception e) {
            ErrorUi.printMarkTaskError();
        }
    }

    //@@author marken9
    private void handleTodo(AppContainer container) {
        int categoryIndex;
        try {
            categoryIndex = CommandSupport.getCategoryIndex(container, sentence);
        } catch (Exception e) {
            TaskUi.printMarkTodoResult(isMark, e.getMessage());
            return;
        }

        ArrayList<String> invalidIndexes = new ArrayList<>();
        int successCount = 0;

        for (int i = INDEX_OF_FIRST_TASK_TO_MARK; i < sentence.length; i++) {
            try {
                int taskIndex = Integer.parseInt(sentence[i]) - 1;

                if (isMark) {
                    container.categories().markTodo(categoryIndex, taskIndex);
                } else {
                    container.categories().unmarkTodo(categoryIndex, taskIndex);
                }
                successCount++;
            } catch (Exception e) {
                invalidIndexes.add(sentence[i]);
            }
        }

        printBatchResult("todo", successCount, invalidIndexes, isMark);
    }

    //@@author WenJunYu5984
    private void handleDeadline(AppContainer container) {
        int categoryIndex;
        try {
            categoryIndex = CommandSupport.getCategoryIndex(container, sentence);
        } catch (Exception e) {
            ErrorUi.printError(e.getMessage());
            return;
        }

        ArrayList<String> invalidIndexes = new ArrayList<>();
        int successCount = 0;

        for (int i = INDEX_OF_FIRST_TASK_TO_MARK; i < sentence.length; i++) {
            try {
                int taskIndex = Integer.parseInt(sentence[i]) - 1;
                container.categories().setDeadlineStatus(categoryIndex, taskIndex, isMark);
                successCount++;
            } catch (Exception e) {
                invalidIndexes.add(sentence[i]);
            }
        }

        printBatchResult("deadline", successCount, invalidIndexes, isMark);
    }

    //@@author sushmiithaa
    private void handleEvent(AppContainer container) {
        int categoryIndex;
        try {
            categoryIndex = CommandSupport.getCategoryIndex(container, sentence);
        } catch (Exception e) {
            ErrorUi.printError(e.getMessage());
            return;
        }

        ArrayList<String> invalidIndexes = new ArrayList<>();
        int successCount = 0;

        for (int i = INDEX_OF_FIRST_TASK_TO_MARK; i < sentence.length; i++) {
            try {
                int taskIndex = Integer.parseInt(sentence[i]) - 1;
                container.categories().setEventStatus(categoryIndex, taskIndex, isMark);
                successCount++;
            } catch (Exception e) {
                invalidIndexes.add(sentence[i]);
            }
        }
        printBatchResult("event", successCount, invalidIndexes, isMark);
    }


    private void printBatchResult(String taskType, int successCount, ArrayList<String> invalidIndexes, boolean isMark) {
        String action = isMark ? "Marked" : "Unmarked";

        if (successCount > 0 && invalidIndexes.isEmpty()) {
            System.out.println(action + " " + successCount + " " + taskType + "(s) successfully.");
            return;
        }

        if (successCount > 0) {
            System.out.println(action + " " + successCount + " " + taskType + "(s) successfully.");
        }

        if (!invalidIndexes.isEmpty()) {
            System.out.println("Skipped invalid indexes: " + String.join(", ", invalidIndexes));
        }

        if (successCount == 0 && !invalidIndexes.isEmpty()) {
            System.out.println("No valid " + taskType + " indexes were provided.");
        }
    }
}