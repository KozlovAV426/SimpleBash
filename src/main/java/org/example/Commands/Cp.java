package org.example.Commands;

import org.example.Commands.Description.Description;
import org.example.State.State;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Description(name = "cp", param = "<src>, <dest>",
        description = "copies <src> file to <dest>")
public class Cp extends Command {
    private void copyDir(Path src, Path dest) {
        try (Stream<Path> walk = Files.walk(src)) {
            walk.forEach(source -> {
                Path destination = Paths.get(dest.toString(), src.getFileName().toString(),
                        src.relativize(source).toString());
                try {
                    Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
            state.setLastCommandFailed(true);
        }
    }

    @Override
    public void execute(String[] argv) {
        if (argv.length < 2) return;

        Path src = Paths.get(argv[0]);
        Path dest = Paths.get(argv[1]);
        Path fileName;

        if (!src.isAbsolute()) {
            src = state.getCurrentDir().resolve(argv[0]);
        }

        fileName = src.getFileName();

        if (!dest.isAbsolute()) {
            dest = state.getCurrentDir().resolve(argv[1]);
        }

        if (src.toFile().isDirectory()) {
            copyDir(src, dest);
            return;
        }

        dest = dest.resolve(fileName);

        try {
            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Cp(State state) {
        this.state = state;
    }
}
