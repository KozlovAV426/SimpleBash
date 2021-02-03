package org.example.Commands;

import org.example.Commands.Description.Description;
import org.example.State.State;


import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Description(name = "cat", param = "<filename>",
        description = "prints lines from <filename>")
public class Cat extends Command {

    private void printFile(Path path) {
        try {
            Files.lines(path, StandardCharsets.UTF_8).forEach(state.getCurrentOut());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            state.setLastCommandFailed(true);
        }
    }

    @Override
    public void execute(String[] argv) {
        if (argv == null) return;

        Path to = Paths.get(argv[0]);
        if (to.isAbsolute()) {
            printFile(to);
        } else {
            printFile(state.getCurrentDir().resolve(argv[0]));
        }
    }

    public Cat(State state) {
        this.state = state;
    }
}
