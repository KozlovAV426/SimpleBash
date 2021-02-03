package org.example.Commands;

import org.example.Commands.Description.Description;
import org.example.State.State;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Description(name = "cd", param = "<dirname>",
        description = "changes directory to <dirname>")
public class Cd extends Command {

    private void moveTo(String[] argv) {
        if (argv == null) return;

        if ("../".equals(argv[0])) {
            if (state.getCurrentDir().getParent() != null) {
                state.setCurrentDir(state.getCurrentDir().getParent());
            }
            return;
        }

        Path to = Paths.get(argv[0]);
        if (to.isAbsolute()) {
            state.setCurrentDir(to);
        } else {
            state.setCurrentDir(state.getCurrentDir().resolve(argv[0]));
        }
    }

    @Override
    public void execute(String[] argv) {
        Path oldPath = state.getCurrentDir();
        moveTo(argv);

        if (Files.notExists(state.getCurrentDir())) {
            System.out.println("Incorrect path");
            state.setCurrentDir(oldPath);
        }
    }

    public Cd(State state) {
        this.state = state;
    }
}
