package seedu.duke.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandHelpTest {

    @Test
    void getHelp_nullTopic_returnsGeneralHelp() {
        String output = CommandHelp.getHelp(null);
        assertTrue(output.contains("UniTasker - Complete Task & Course Management System"));
        assertTrue(output.contains("help task"));
        assertTrue(output.contains("help commands"));
    }

    @Test
    void getHelp_taskAlias_returnsTaskHelp() {
        String output = CommandHelp.getHelp("todo");
        assertTrue(output.contains("TASK MANAGEMENT COMMANDS"));
        assertTrue(output.contains("add todo <cat_idx> <description>"));
        assertTrue(output.contains("mark todo <cat_idx> <idx> [<idx> ...]"));
    }

    @Test
    void getHelp_courseTopic_returnsCourseHelp() {
        String output = CommandHelp.getHelp("course");
        assertTrue(output.contains("COURSE TRACKER COMMANDS"));
        assertTrue(output.contains("course add-assessment <code> /n <name> /w <weightage> /ms <max_score>"));
        assertTrue(output.contains("course delete-assessment <code> /n <assessment_name>"));
    }

    @Test
    void getHelp_commandsAlias_returnsCommandSummary() {
        String output = CommandHelp.getHelp("commands");
        assertTrue(output.contains("COMMAND SUMMARY"));
        assertTrue(output.contains("course score <CODE> /n <NAME> /s <SCORE>"));
        assertTrue(output.contains("delete marked"));
        assertTrue(output.contains("reorder category <FROM> <TO>"));
    }

    @Test
    void getHelp_unknownTopic_returnsUnknownTopicMessage() {
        String output = CommandHelp.getHelp("nonsense");
        assertTrue(output.contains("Unknown help topic: 'nonsense'"));
        assertTrue(output.contains("help course"));
    }
}
