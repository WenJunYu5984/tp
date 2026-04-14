package seedu.duke.ui;

/**
 * Centralized help system for UniTasker.
 * Provides contextual help for each mode and command.
 */
//@@author benguy6
public class CommandHelp {
    private static final String DOTTED_LINE = "______________________________________________________________________";

    /**
     * Get general help showing all 4 modes and basic commands.
     */
    public static String getGeneralHelp() {
        return DOTTED_LINE + "\n" +
                "UniTasker - Complete Task & Course Management System\n" +
                "\n" +
                "4 FEATURE MODES:\n" +
                "  1. Todo Management    - Manage todos with priority & organization\n" +
                "  2. Deadline Manager   - Track assignments with auto-sorted dates\n" +
                "  3. Event Manager      - Schedule events and recurring meetings\n" +
                "  4. Course Tracker     - Manage courses and assessment grades\n" +
                "\n" +
                "SYSTEM COMMANDS:\n" +
                "  find <keyword>        - Search for tasks by keyword\n" +
                "  limit task <num>      - Set daily task limit (default: 8)\n" +
                "  limit year <num>      - Set end year for dates (default: 2030)\n" +
                "  list limit            - Show current limits\n" +
                "  reminder              - Show all pending tasks for the day\n" +
                "  undo                  - Undo the last undoable command\n" +
                "  exit                  - Quit UniTasker\n" +
                "\n" +
                "GETTING HELP:\n" +
                "  help                  - Show this message\n" +
                "  help task             - Show task management commands\n" +
                "  help deadline         - Show deadline management commands\n" +
                "  help event            - Show event management commands\n" +
                "  help course           - Show course tracker commands\n" +
                "  help add              - Show all 'add' command variants\n" +
                "  help delete           - Show all 'delete' command variants\n" +
                "  help list             - Show all 'list' command variants\n" +
                "  help commands         - Show the full command summary\n" +
                "\n" +
                "COMMAND ALIASES: a=add, d=delete, l=list, m=mark, u=unmark, p=priority, s=sort, f=find\n" +
                DOTTED_LINE;
    }

    /**
     * Get help for Task Management mode.
     */
    public static String getTaskHelp() {
        return DOTTED_LINE + "\n" +
                "TASK MANAGEMENT COMMANDS\n" +
                "Manage todos with priority levels (0-5) within categories.\n" +
                "\n" +
                "ADD TODOS:\n" +
                "  add todo <cat_idx> <description> [/p <priority>]\n" +
                "  Example: add todo 1 Review CS2113 notes\n" +
                "\n" +
                "MARK/UNMARK:\n" +
                "  mark todo <cat_idx> <idx> [<idx> ...]\n" +
                "  unmark todo <cat_idx> <idx> [<idx> ...]\n" +
                "  Example: mark todo 1 1\n" +
                "\n" +
                "SET PRIORITY (0=low, 5=high):\n" +
                "  priority todo <cat_idx> <idx> <priority>\n" +
                "  Example: priority todo 1 2 4\n" +
                "\n" +
                "REORDER/SORT:\n" +
                "  reorder category <idx1> <idx2>\n" +
                "  reorder todo <cat_idx> <idx1> <idx2>\n" +
                "  sort todo <cat_idx>          - Sort by priority\n" +
                "\n" +
                "DELETE:\n" +
                "  delete todo <cat_idx> <idx>\n" +
                "  delete todo <cat_idx> all\n" +
                "\n" +
                "LIST:\n" +
                "  list todo                    - List all todos\n" +
                "  list category                - List all categories\n" +
                "  list category <idx>          - List specific category\n" +
                DOTTED_LINE;
    }

    /**
     * Get help for Deadline Management mode.
     */
    public static String getDeadlineHelp() {
        return DOTTED_LINE + "\n" +
                "DEADLINE MANAGEMENT COMMANDS\n" +
                "Track assignments with auto-sorted dates and workload validation.\n" +
                "Date format: dd-MM-yyyy | Time format: HHmm (24-hour)\n" +
                "\n" +
                "ADD DEADLINE:\n" +
                "  add deadline <cat_idx> <description> /by <date> <time>\n" +
                "  Example: add deadline 1 Submit essay /by 25-03-2026 2359\n" +
                "\n" +
                "MARK/UNMARK:\n" +
                "  mark deadline <cat_idx> <idx> [<idx> ...]\n" +
                "  unmark deadline <cat_idx> <idx> [<idx> ...]\n" +
                "\n" +
                "DELETE:\n" +
                "  delete deadline <cat_idx> <idx>\n" +
                "  delete deadline <cat_idx> all    - Delete all deadlines in category\n" +
                "\n" +
                "LIST:\n" +
                "  list deadline                    - List all deadlines\n" +
                "  list range <start> <end> [/deadline]\n" +
                "  Example: list range 25-03-2026 31-03-2026 /deadline\n" +
                DOTTED_LINE;
    }

    /**
     * Get help for Event Management mode.
     */
    public static String getEventHelp() {
        return DOTTED_LINE + "\n" +
                "EVENT MANAGEMENT COMMANDS\n" +
                "Schedule events, meetings, and recurring activities.\n" +
                "Date format: dd-MM-yyyy | Time format: HHmm (24-hour) | Days: Monday-Sunday\n" +
                "\n" +
                "ADD EVENT (One-time):\n" +
                "  add event <cat_idx> <desc> /from <date> <time> /to <date> <time>\n" +
                "  Example: add event 1 Lecture /from 25-03-2026 1400 /to 25-03-2026 1530\n" +
                "\n" +
                "ADD RECURRING EVENT (Weekly):\n" +
                "  add recurring <cat_idx> <desc>\n" +
                "  /from <day> <time> /to <day> <time> [/month <num> | /date <date>]\n" +
                "  Example: add recurring 1 Lecture\n" +
                "           /from Monday 1400 /to Monday 1530 /month 3\n" +
                "  - /month N  : Repeat for N months\n" +
                "  - /date END : Repeat until END date (format: dd-MM-yyyy)\n" +
                "\n" +
                "MARK/UNMARK:\n" +
                "  mark event <cat_idx> <idx> [<idx> ...]\n" +
                "  unmark event <cat_idx> <idx> [<idx> ...]\n" +
                "  mark occurrence <cat_idx> <idx> [<idx> ...]\n" +
                "  unmark occurrence <cat_idx> <idx> [<idx> ...]\n" +
                "\n" +
                "DELETE:\n" +
                "  delete event <cat_idx> <idx>\n" +
                "  delete event <cat_idx> all\n" +
                "  delete recurring <cat_idx> <id>\n" +
                "  delete occurrence <cat_idx> <id>\n" +
                "\n" +
                "LIST:\n" +
                "  list event                     - List events with recurring events collapsed to one group\n" +
                "  list event /all                - List all events\n" +
                "  list event /normal             - List only non-recurring events\n" +
                "  list recurring                 - List recurring events\n" +
                "  list occurrence <cat_idx> <id> - List occurrence of recurring event group\n" +
                "  list range <start> <end> [/event]\n" +
                DOTTED_LINE;
    }

    /**
     * Get help for Course Tracker mode.
     */
    public static String getCourseHelp() {
        return DOTTED_LINE + "\n" +
                "COURSE TRACKER COMMANDS\n" +
                "Manage courses and track assessment grades.\n" +
                "\n" +
                "ADD/LIST COURSES:\n" +
                "  course add <code>                - Add a new course\n" +
                "  course list                      - List all courses\n" +
                "  course view <code>               - View course assessments\n" +
                "  Example: course add CS2113\n" +
                "\n" +
                "ADD ASSESSMENT:\n" +
                "  course add-assessment <code> /n <name> /w <weightage> /ms <max_score>\n" +
                "  Example: course add-assessment CS2113 /n Finals /w 40 /ms 100\n" +
                "  - /n  : Assessment name\n" +
                "  - /w  : Weightage percentage\n" +
                "  - /ms : Maximum score\n" +
                "\n" +
                "RECORD SCORE:\n" +
                "  course score <code> /n <assessment_name> /s <score_obtained>\n" +
                "  Example: course score CS2113 /n Finals /s 85\n" +
                "\n" +
                "DELETE:\n" +
                "  course delete <code>\n" +
                "  course delete-assessment <code> /n <assessment_name>\n" +
                DOTTED_LINE;
    }

    /**
     * Get help for 'add' command across all modes.
     */
    public static String getAddCommandHelp() {
        return DOTTED_LINE + "\n" +
                "ADD COMMAND VARIANTS\n" +
                "\n" +
                "CATEGORIES:\n" +
                "  add category <name>\n" +
                "  Example: add category CS2113\n" +
                "\n" +
                "TODOS:\n" +
                "  add todo <cat_idx> <description> [/p <priority>]\n" +
                "  Example: add todo 1 Review notes /p 3\n" +
                "\n" +
                "DEADLINES:\n" +
                "  add deadline <cat_idx> <desc> /by <date> <time>\n" +
                "  Example: add deadline 1 Submit /by 25-03-2026 2359\n" +
                "\n" +
                "EVENTS:\n" +
                "  add event <cat_idx> <desc> /from <date> <time> /to <date> <time>\n" +
                "  Example: add event 1 Meeting /from 25-03-2026 1400 /to 25-03-2026 1500\n" +
                "\n" +
                "RECURRING EVENTS:\n" +
                "  add recurring <cat_idx> <desc> /from <day> <time> /to <day> <time> [/month <num> | /date <date>]\n" +
                "  Example: add recurring 1 Lecture /from Monday 1400 /to Monday 1530 /month 3\n" +
                "\n" +
                "COURSES:\n" +
                "  course add <code>\n" +
                "  Example: course add CS2113\n" +
                "\n" +
                "ASSESSMENTS:\n" +
                "  course add-assessment <code> /n <name> /w <weightage> /ms <max_score>\n" +
                "  Example: course add-assessment CS2113 /n Finals /w 40 /ms 100\n" +
                DOTTED_LINE;
    }

    /**
     * Get help for 'delete' command across all modes.
     */
    public static String getDeleteCommandHelp() {
        return DOTTED_LINE + "\n" +
                "DELETE COMMAND VARIANTS\n" +
                "\n" +
                "CATEGORIES:\n" +
                "  delete category <idx>\n" +
                "\n" +
                "TODOS:\n" +
                "  delete todo <cat_idx> <idx>\n" +
                "  delete todo <cat_idx> all\n" +
                "\n" +
                "DEADLINES:\n" +
                "  delete deadline <cat_idx> <idx>\n" +
                "  delete deadline <cat_idx> all\n" +
                "\n" +
                "EVENTS:\n" +
                "  delete event <cat_idx> <idx>\n" +
                "  delete event <cat_idx> all\n" +
                "\n" +
                "RECURRING:\n" +
                "  delete recurring <cat_idx> <idx>\n" +
                "\n" +
                "OCCURRENCE:\n" +
                "  delete occurrence <cat_idx> <idx>\n" +
                "\n" +
                "SYSTEM:\n" +
                "  delete marked\n" +
                "\n" +
                "COURSES:\n" +
                "  course delete <code>\n" +
                "\n" +
                "ASSESSMENTS:\n" +
                "  course delete-assessment <code> /n <assessment_name>\n" +
                DOTTED_LINE;
    }

    /**
     * Get help for 'list' command across all modes.
     */
    public static String getListCommandHelp() {
        return DOTTED_LINE + "\n" +
                "LIST COMMAND VARIANTS\n" +
                "\n" +
                "CATEGORIES:\n" +
                "  list category                - List all categories\n" +
                "  list category <idx>          - List specific category\n" +
                "\n" +
                "TODOS:\n" +
                "  list todo                    - List all todos\n" +
                "\n" +
                "DEADLINES:\n" +
                "  list deadline                - List all deadlines\n" +
                "\n" +
                "EVENTS:\n" +
                "  list event                     - List events with recurring events collapsed to one group\n" +
                "  list event /all                - List all events\n" +
                "  list event /normal             - List only non-recurring events\n" +
                "  list recurring                 - List recurring events\n" +
                "  list occurrence <cat_idx> <id> - List occurrence of recurring event group\n" +
                "\n" +
                "DATE RANGES (all types):\n" +
                "  list range <start> <end>           - All tasks in range\n" +
                "  list range <start> <end> /deadline - Only deadlines\n" +
                "  list range <start> <end> /event    - Only events\n" +
                "  Example: list range 25-03-2026 31-03-2026 /deadline\n" +
                "\n" +
                "COURSES:\n" +
                "  course list                  - List all courses\n" +
                "  course view <code>           - View assessments\n" +
                "\n" +
                "SYSTEM:\n" +
                "  list limit                   - Show current limits\n" +
                DOTTED_LINE;
    }

    /**
     * Get the complete command summary table (from User Guide).
     */
    public static String getCommandSummary() {
        return DOTTED_LINE + "\n" +
                "COMMAND SUMMARY\n" +
                "\n" +
                "TASK MANAGEMENT:\n" +
                "  add todo <CAT_IDX> <DESC> [/p <PRIORITY>]\n" +
                "    Example: add todo 1 Review notes\n" +
                "  add deadline <CAT_IDX> <DESC> /by <DATE> [<TIME>]\n" +
                "    Example: add deadline 1 Submit essay /by 25-05-2026 1800\n" +
                "  add event <CAT_IDX> <DESC> /from <DATE> <TIME> /to <DATE> <TIME>\n" +
                "    Example: add event 1 Meeting /from 25-05-2026 1400 /to 25-05-2026 1600\n" +
                "  add recurring <CAT_IDX> <DESC> /from <DAY> <TIME> /to <DAY> <TIME> [/month <NUM> | /date <DATE>]\n" +
                "    Example: add recurring 1 Lecture /from Monday 1400 /to Monday 1530 /month 3\n" +
                "  mark/unmark <TASKTYPE> <CAT_IDX> <IDX> [<IDX> ...]\n" +
                "    Example: mark todo 1 1\n" +
                "  priority todo <CAT_IDX> <IDX> <PRIORITY>\n" +
                "    Example: priority todo 1 2 4\n" +
                "  sort todo <CAT_IDX>              - Sort by priority\n" +
                "  reorder category <FROM> <TO>\n" +
                "  reorder <TYPE> <CAT_IDX> <FROM> <TO>\n" +
                "    Example: reorder todo 1 1 3\n" +
                "\n" +
                "LISTING:\n" +
                "  list category [<IDX>]\n" +
                "  list todo                        - List all todos\n" +
                "  list deadline                    - List all deadlines\n" +
                "  list event [/all | /normal]      - List events\n" +
                "  list recurring                   - List recurring events\n" +
                "  list occurrence <CAT_IDX> <IDX>  - List occurrences\n" +
                "  list range <START> <END> [/TYPE] - List by date range\n" +
                "  list limit                       - Show current limits\n" +
                "\n" +
                "DELETION:\n" +
                "  delete category <IDX>\n" +
                "  delete <TASKTYPE> <CAT_IDX> <IDX>\n" +
                "  delete <TASKTYPE> <CAT_IDX> all  - Delete all of type\n" +
                "  delete marked\n" +
                "\n" +
                "COURSE TRACKER:\n" +
                "  course add <CODE>\n" +
                "  course delete <CODE>\n" +
                "  course list                      - List all courses\n" +
                "  course view <CODE>               - View assessments\n" +
                "  course add-assessment <CODE> /n <NAME> /w <WEIGHTAGE> /ms <MAX_SCORE>\n" +
                "    Example: course add-assessment CS2113 /n Finals /w 40 /ms 100\n" +
                "  course score <CODE> /n <NAME> /s <SCORE>\n" +
                "  course delete-assessment <CODE> /n <NAME>\n" +
                "\n" +
                "SYSTEM:\n" +
                "  find <KEYWORD>                   - Search tasks\n" +
                "  limit task <NUM>                 - Set daily task limit (default: 8)\n" +
                "  limit year <NUM>                 - Set end year (default: 2030)\n" +
                "  reminder                         - Show pending tasks\n" +
                "  undo                             - Undo last command\n" +
                "  exit                             - Quit UniTasker\n" +
                "\n" +
                "ALIASES: a=add, d=delete, l=list, m=mark, u=unmark, p=priority, s=sort, f=find\n" +
                "DATE FORMAT: dd-MM-yyyy | TIME FORMAT: HHmm (24-hour) | DAYS: Monday-Sunday\n" +
                DOTTED_LINE;
    }

    /**
     * Route help requests to appropriate method.
     */
    public static String getHelp(String topic) {
        if (topic == null || topic.isEmpty() || topic.equalsIgnoreCase("general")) {
            return getGeneralHelp();
        }

        return switch (topic.toLowerCase()) {
        case "task", "todo" -> getTaskHelp();
        case "deadline" -> getDeadlineHelp();
        case "event" -> getEventHelp();
        case "course" -> getCourseHelp();
        case "add" -> getAddCommandHelp();
        case "delete" -> getDeleteCommandHelp();
        case "list" -> getListCommandHelp();
        case "summary", "commands", "cheatsheet" -> getCommandSummary();
        default -> "Unknown help topic: '" + topic + "'\n" +
                "Try: help, help task, help deadline, help event," +
                " help course, help add, help delete, help list, help commands\n" +
                DOTTED_LINE;
        };
    }
}
