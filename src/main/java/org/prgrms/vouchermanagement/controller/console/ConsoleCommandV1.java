package org.prgrms.vouchermanagement.controller.console;

public enum ConsoleCommandV1 {
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
