package org.prgrms;

import org.prgrms.console.Console;
import org.springframework.stereotype.Component;

@Component
public class VoucherProcessManager {

  private final Console console;

  public VoucherProcessManager(Console console) {
    this.console = console;
  }

  public void execute() {
    MenuType menu = MenuType.of(console.choiceMenu());

    switch (menu) {
      case EXIT -> exit();

      case CREATE -> {

      }
      case LIST -> {

      }
      default -> console.commandNotFound();
    }
  }

  private void exit() {
    System.exit(0);
  }
}
