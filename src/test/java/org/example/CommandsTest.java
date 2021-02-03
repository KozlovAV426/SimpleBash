package org.example;

import org.example.Commands.*;
import org.example.State.State;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class CommandsTest {
    public State state;
    public static int iter = 0;

    @Before
    public void init() {
        state = new State();
    }

    public void createFile(String name, String[] content) {
        Path path = Paths.get(state.getCurrentDir().resolve(name).toString());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            for (int i = 0; i < content.length; ++i) {
                writer.write(content[i]);
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String name) {
        try {
            Files.delete(state.getCurrentDir().resolve(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void catTest() {
        Cat cat = new Cat(state);
        String fileName = "testFile";

        String[] content = {"hello", "!", "how", "are", "you", "?"};

        createFile(fileName, content);

        iter = 0;
        state.setCurrentOut((line) -> {
            assertEquals(line, content[iter]);
            ++iter;
        });

        cat.execute(new String[] {fileName});
        assertEquals(iter, content.length);
        deleteFile(fileName);
    }

    @Test
    public void tailTest() {
        Tail tail = new Tail(state);
        String fileName = "testFile";

        String[] content = {"hello", "!", "how", "are", "you", "?"};

        createFile(fileName, content);

        int lastN = 3;

        iter = content.length - lastN;
        state.setCurrentOut((line) -> {
            assertEquals(line, content[iter]);
            ++iter;
        });

        tail.execute(new String[] {fileName, "-n", Integer.valueOf(lastN).toString()});
        assertEquals(iter, content.length);
        deleteFile(fileName);
    }



    @Test
    public void touchTest() {
        Touch touch = new Touch(state);
        String fileName = "testFile";
        touch.execute(new String[] {fileName});

        assertTrue(Files.exists(state.getCurrentDir().resolve(fileName)));

        deleteFile(fileName);
    }

    @Test
    public void mkdirTest() {
        Mkdir mkdir = new Mkdir(state);
        String dirName = "testDir";
        mkdir.execute(new String[] {dirName});

        assertTrue(Files.exists(state.getCurrentDir().resolve(dirName)));

        deleteFile(dirName);
    }

    @Test
    public void cdTest() {
        Cd cd = new Cd(state);
        String[] to = new String[] {"../"};

        for (int i = 0; i < 50; ++i) {
            try {
                cd.execute(to);
            } catch (Exception e) {
                fail();
            }
        }

        String[] noDir = new String[] {Long.valueOf(
                ThreadLocalRandom.current().nextLong(
                        1000, 10000)).toString()
        };

        try {
            cd.execute(noDir);
        } catch (Exception e) {
            fail();
        }

    }
}
