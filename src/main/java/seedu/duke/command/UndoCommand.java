package seedu.duke.command;

import seedu.duke.UniTasker;
import seedu.duke.appcontainer.AppContainer;
import seedu.duke.ui.ErrorUi;

public class UndoCommand implements Command {

    @Override
    public void execute(AppContainer container) {
        if (UniTasker.getCommandHistory().isEmpty()) {
            ErrorUi.printError("Nothing to undo.");
            return;
        }
        Command last = UniTasker.getCommandHistory().pop();
        last.undo(container);
    }
}
