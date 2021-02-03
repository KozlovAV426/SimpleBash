package org.example.State;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class State {
    private Path currentDir;
    private boolean isWorking;
    private boolean isLastCommandFailed;
    private Consumer<String> currentOut;

    public Path getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(Path currentDir) {
        this.currentDir = currentDir;
    }

    public State() {
        currentDir = Paths.get(System.getProperty("user.home"));
        isWorking = true;
        isLastCommandFailed = false;
        currentOut = System.out::println;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setLastCommandFailed(boolean lastCommandFailed) {
        isLastCommandFailed = lastCommandFailed;
    }

    public boolean isLastCommandFailed() {
        return isLastCommandFailed;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public Consumer<String> getCurrentOut() {
        return currentOut;
    }

    public void setCurrentOut(Consumer<String> currentOut) {
        this.currentOut = currentOut;
    }
}
