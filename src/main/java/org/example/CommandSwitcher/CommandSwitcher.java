package org.example.CommandSwitcher;

import org.example.Commands.*;
import org.example.State.State;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class CommandSwitcher {
    private final State state;
    private final HashMap<String, Command> commands;

    public void processCommand(String command, String[] argv, String fileName) {
        if (null != fileName) {
            Path path = Paths.get(fileName);
            if (!path.isAbsolute()) {
                path = state.getCurrentDir().resolve(fileName);
            }

            try (PrintWriter printWriter = new PrintWriter(Files.newBufferedWriter(
                    path, StandardCharsets.UTF_8))) {
                state.setCurrentOut((line) -> {
                    printWriter.println(line);
                });

                try {
                    commands.get(command).execute(argv);
                } catch (NullPointerException e) {
                    System.out.println("No such command");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {

            try {
                commands.get(command).execute(argv);
            } catch (NullPointerException e) {
                System.out.println("No such command");
            }
        }

        state.setCurrentOut(System.out::println);
    }

    public boolean isWorking() {
        return state.isWorking();
    }
    public boolean isLastCommandFailed() {
        return state.isLastCommandFailed();
    }
    public Path getCurrentDir() {
        return state.getCurrentDir();
    }

    public CommandSwitcher() {
        state = new State();

        commands = new HashMap<>();

        commands.put("ls", new Ls(state));
        commands.put("cd", new Cd(state));
        commands.put("cat", new Cat(state));
        commands.put("mkdir", new Mkdir(state));
        commands.put("touch", new Touch(state));
        commands.put("tail", new Tail(state));
        commands.put("help", new Help(state));
        commands.put("exit", new Exit(state));
        commands.put("cp", new Cp(state));

    }

}
