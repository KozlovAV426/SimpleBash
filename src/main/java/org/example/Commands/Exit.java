package org.example.Commands;

import org.example.Commands.Description.Description;
import org.example.State.State;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Description(name = "exit", param = "(no args)",
        description = "stops bash")
public class Exit extends Command {
    @Override
    public void execute(String[] argv) {
       state.setWorking(false);
    }

    public Exit(State state) {
        this.state = state;
    }
}
