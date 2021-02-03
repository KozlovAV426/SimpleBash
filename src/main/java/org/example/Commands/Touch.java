package org.example.Commands;

import org.example.Commands.Description.Description;
import org.example.State.State;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;


@Description(name = "touch", param = "<filename>",
        description = "creates <filename> in current directory")
public class Touch extends Command {
    @Override
    public void execute(String[] argv) {
        if (argv == null) return;

        try {
            Files.createFile(state.getCurrentDir().resolve(argv[0]));
        } catch (FileAlreadyExistsException e) {
            try (BufferedWriter bw = Files.newBufferedWriter(state.getCurrentDir().resolve(argv[0]))) {
            }
            catch (IOException innerE) {
                System.out.println(innerE.getMessage());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            state.setLastCommandFailed(true);
        }
    }

    public Touch(State state) {
        this.state = state;
    }
}
