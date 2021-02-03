package org.example.Commands;

import org.example.Commands.Description.Description;
import org.example.State.State;


import java.nio.file.Files;

@Description(name = "mkdir", param = "<dirname>",
        description = "creates directory with <dirname> in current directory")
public class Mkdir extends Command {
    @Override
    public void execute(String[] argv) {
        if (argv == null) return;

        try {
            Files.createDirectory(state.getCurrentDir().resolve(argv[0]));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            state.setLastCommandFailed(true);
        }
    }

    public Mkdir(State state) {
        this.state = state;
    }
}
