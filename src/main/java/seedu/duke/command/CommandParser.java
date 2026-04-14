package seedu.duke.command;

public class CommandParser {

    public static final int INDEX_OF_COMMANDTYPE = 0;
    private static final String EMPTY_COMMAND = "";

    //@@author marken9
    public Command parse(String line) {
        if (line == null) {
            return new UnknownCommand(EMPTY_COMMAND);
        }

        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
            return new UnknownCommand(EMPTY_COMMAND);
        }

        if (trimmedLine.contains("|")) {
            return new UnknownCommand("|");
        }
        String[] sentence = trimmedLine.split("\\s+");
        String commandWord = sentence[INDEX_OF_COMMANDTYPE].toLowerCase();

        // Map aliases to full command names
        commandWord = mapAliasToCommand(commandWord);

        switch (commandWord) {
        case "add":
            return new AddCommand(sentence);
        case "delete":
            return new DeleteCommand(sentence);
        case "mark":
            return new MarkCommand(sentence, true);
        case "unmark":
            return new MarkCommand(sentence, false);
        case "reorder":
            return new ReorderCommand(sentence);
        case "priority":
            return new PriorityCommand(sentence);
        case "sort":
            return new SortCommand(sentence);
        case "list":
            return new ListCommand(sentence);
        case "find":
            return new FindCommand(sentence);
        case "course":
            return new CourseCommand(trimmedLine);
        case "limit":
            return new LimitCommand(sentence);
        case "help":
            return new HelpCommand(sentence);
        case "reminder":
            return new ReminderCommand();
        case "exit":
            return new ExitCommand();
        case "undo":
            return new UndoCommand();
        default:
            return new UnknownCommand(sentence[0]);
        }
    }

    /**
     * Maps command aliases to their full command names.
     * Supports: a=add, d=delete, l=list, m=mark, u=unmark, p=priority, s=sort, f=find
     */
    private String mapAliasToCommand(String commandWord) {
        return switch (commandWord) {
        case "a" -> "add";
        case "d" -> "delete";
        case "l" -> "list";
        case "m" -> "mark";
        case "u" -> "unmark";
        case "p" -> "priority";
        case "s" -> "sort";
        case "f" -> "find";
        default -> commandWord;
        };
    }
}
