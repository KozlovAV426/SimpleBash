package org.example.SimpleBash;

import org.example.CommandSwitcher.CommandSwitcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimpleBash {
    private CommandSwitcher switcher;
    private void pwd() {
        System.out.printf("> %s\n", switcher.getCurrentDir());
    }

    public SimpleBash() {
        switcher = new CommandSwitcher();
    }

    public void start() {
        pwd();

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            while (switcher.isWorking()) {

                String line = bf.readLine();
                String[] words = line.split(" ");


                boolean needNextExec = true;
                int maxArgN = 3;
                String[] argv = new String[maxArgN];
                String command = "";
                String fileName = null;

                for (int i = 0; i < words.length; ++i) {
                    command = words[i];
                    int j = 0;
                    ++i;
                    while (i < words.length && !"&&".equals(words[i]) &&
                            ! "||".equals(words[i]) && ! ">".equals(words[i])) {
                        argv[j % maxArgN] = words[i];
                        ++i;
                        ++j;
                    }

                    if (i < words.length && ">".equals(words[i])) {
                        fileName = i + 1 < words.length ? words[i + 1] : null;
                        while (i < words.length && !"&&".equals(words[i]) &&
                                ! "||".equals(words[i])) ++i;
                    }

                    if (needNextExec) {
                        switcher.processCommand(command, argv, fileName);
                        pwd();
                    }

                    if (i < words.length && "&&".equals(words[i])) {
                        needNextExec = !switcher.isLastCommandFailed();
                    } else if (i < words.length && "||".equals(words[i])) {
                        needNextExec = switcher.isLastCommandFailed();
                    }
                    fileName = null;
                }

            }} catch (IOException e) {
                System.out.println(e.getMessage());
        }
    }
}
