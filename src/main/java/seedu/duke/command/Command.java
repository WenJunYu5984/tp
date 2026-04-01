package seedu.duke.command;

import seedu.duke.appcontainer.AppContainer;

public interface Command {
    void execute(AppContainer container);

    default void undo(AppContainer container) {}

    default boolean isUndoable() {
        return false;
    }
}
