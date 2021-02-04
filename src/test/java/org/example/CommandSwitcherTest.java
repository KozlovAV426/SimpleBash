package org.example;

import static org.junit.Assert.fail;

import org.example.CommandSwitcher.CommandSwitcher;
import org.junit.Test;


public class CommandSwitcherTest {
    @Test
    public void processUnknownCommand() {
        CommandSwitcher switcher = new CommandSwitcher();
        String[] dummyCommands = new String[] {"", "qqq", "12", "!!!!!"};
        for (String command : dummyCommands) {
            try {
                switcher.processCommand(command, null, null);
            } catch (Exception e) {
                fail();
            }
        }
    }
}
