package org.example.Commands;

import org.example.State.State;

public abstract class Command {
    protected State state;
    public abstract void execute(String[] argv);
}
