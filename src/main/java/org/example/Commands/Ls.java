package org.example.Commands;

import org.example.Commands.Description.Description;
import org.example.State.State;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.stream.Stream;


@Description(name = "ls", param = "(no args)",
        description = "prints list of files in current directory")
public class Ls extends Command {

    private void printAttributes(Path path) {
        PosixFileAttributes posix = null;
        try {
            posix = Files.readAttributes(path, PosixFileAttributes.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            state.setLastCommandFailed(true);
            return;
        }
        state.getCurrentOut().accept(path + " " + PosixFilePermissions.toString(posix.permissions()));
    }

    @Override
    public void execute(String[] argv) {
        try {
            Stream<Path> list = Files.list(state.getCurrentDir());
            list.forEach(this::printAttributes);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            state.setLastCommandFailed(true);
        }
    }

    public Ls(State state) {
        this.state = state;
    }

}
