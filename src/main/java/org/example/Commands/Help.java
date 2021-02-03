package org.example.Commands;

import org.example.Commands.Description.Description;
import org.example.State.State;


@Description(name = "help", param = "(no args)",
        description = "prints list of available commands and their description")
public class Help extends Command {
    private final String[] classNames = {"Cat", "Cd", "Cp", "Exit",
            "Help", "Ls", "Mkdir", "Tail", "Touch"};

    @Override
    public void execute(String[] argv) {
        state.getCurrentOut().accept("Here is a list of supported commands");
        try {
            for (String commandName : classNames) {
                Class cls = Class.forName("org.example.Commands." + commandName);
                Description descr = (Description) cls.getDeclaredAnnotation(Description.class);
                state.getCurrentOut().accept(
                        descr.name() + " " +  descr.param() + " " + descr.description());
            }

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            state.setLastCommandFailed(true);
        }
    }

    public Help(State state) {
        this.state = state;
    }
}
