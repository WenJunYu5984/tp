package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelpCommandTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void redirect() {
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void restore() {
        System.setOut(originalOut);
    }

    @Test
    void parser_helpWithoutTopic_printsGeneralHelp() {
        Command command = new CommandParser().parse("help");
        command.execute(null);

        String output = out.toString();
        assertTrue(output.contains("UniTasker - Complete Task & Course Management System"));
        assertTrue(output.contains("help commands"));
    }

    @Test
    void parser_helpTopLevelMixedCase_printsGeneralHelp() {
        Command command = new CommandParser().parse("HeLp");
        command.execute(null);

        String output = out.toString();
        assertTrue(output.contains("UniTasker - Complete Task & Course Management System"));
    }

    @Test
    void parser_helpWithMixedCaseTopic_printsTopicHelp() {
        Command command = new CommandParser().parse("help CoUrSe");
        command.execute(null);

        String output = out.toString();
        assertTrue(output.contains("COURSE TRACKER COMMANDS"));
        assertTrue(output.contains("course add-assessment <code> /n <name> /w <weightage> /ms <max_score>"));
    }

    @Test
    void parser_helpCommands_printsCommandSummary() {
        Command command = new CommandParser().parse("help commands");
        command.execute(null);

        String output = out.toString();
        assertTrue(output.contains("COMMAND SUMMARY"));
        assertTrue(output.contains("delete marked"));
    }

    @Test
    void parser_unknownHelpTopic_printsUnknownTopicMessage() {
        Command command = new CommandParser().parse("help mystery");
        command.execute(null);

        String output = out.toString();
        assertTrue(output.contains("Unknown help topic: 'mystery'"));
        assertTrue(output.contains("help course"));
    }

    @Test
    void parser_helpWithExtraArguments_printsFormatError() {
        Command command = new CommandParser().parse("help course extra");
        command.execute(null);

        String output = out.toString();
        assertTrue(output.contains("help failed: Too many arguments."));
        assertTrue(output.contains("Correct format: help [topic]"));
    }

    @Test
    void parser_blankInput_producesNoOutput() {
        Command command = new CommandParser().parse("   ");
        command.execute(null);

        assertEquals("", out.toString());
    }
}
