package org.prgrms.vouchermanagement.controller.console;

import java.util.function.Function;

public enum ConsoleCommand {
  EXIT, LIST, CREATE;
  public boolean apply(ConsoleController consoleController) {
    switch(this) {
      case EXIT:
        return false;
      case LIST:
        consoleController.processList();
        return true;
      default:
        consoleController.nonExistentCommand();
        return true;
    }
  }
}
