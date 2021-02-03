package org.example.Commands;

import org.example.Commands.Description.Description;
import org.example.State.State;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Description(name = "tail", param = "[number of lines to print]",
        description = "prints last N lines of file")
public class Tail extends Command {

    private  void printLastNLines(Path path, int num) {
      try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
          String line;
          boolean isOverFlow = false;
          String[] lines = new String[num];
          int it = 0;
          while ((line = br.readLine()) != null) {
              lines[it] = line;
              it++;
              if (it == num) {
                  isOverFlow = true;
                  it -= num;
              }
          }

          if (isOverFlow) {
              for (int i = it; i < num; ++i) {
                  state.getCurrentOut().accept(lines[i]);
              }
          }

          for (int i = 0; i < it; ++i) {
              state.getCurrentOut().accept(lines[i]);
          }
      } catch (IOException e) {
          System.out.println(e.getMessage());
          state.setLastCommandFailed(true);
      }
    }

    @Override
    public void execute(String[] argv) {
        if (argv == null) return;

        Path to = Paths.get(argv[0]);
        if (!to.isAbsolute()) {
            to = state.getCurrentDir().resolve(argv[0]);
        }

        if ("-n".equals(argv[1])) {
            printLastNLines(to, Integer.parseInt(argv[2]));
        } else {
            printLastNLines(to, 10);
        }
    }

    public Tail(State state) {
        this.state = state;
    }
}
