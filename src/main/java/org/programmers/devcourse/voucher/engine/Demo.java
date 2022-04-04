package org.programmers.devcourse.voucher.engine;

import java.util.Optional;
import org.programmers.devcourse.voucher.engine.io.Input;
import org.programmers.devcourse.voucher.engine.io.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Demo {

  private final Input input;
  private final Output output;

  public Demo(@Autowired Input input, @Autowired Output output) {
    this.input = input;
    this.output = output;
  }


  public void start() {

    try (input; output) {

      while (true) {
        Optional<Selection> optionalSelection = input.getSelection();
        if (optionalSelection.isEmpty()) {
          output.print("Wrong command");
          continue;
        }

        switch (optionalSelection.get()) {
          case EXIT:
            output.print("Good Bye");
            return;
          case CREATE:
            output.print("CREATE!");
            break;
          case LIST:
            output.print("LIST!");
        }


      }

    } catch (Exception e) {
      e.printStackTrace();
      output.print("Error! terminating application");
    }

  }
}
