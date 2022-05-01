package org.prgrms.vouchermanagement.controller.console;

import java.util.function.Function;

public enum ConsoleCommandV2 {
  EXIT("exit", job -> {
    return false;
  }),
  LIST("list", job -> {
    job.processList();
    return true;
  }),
  CREATE("create", job -> {
    return true;
  });

  private final String command;
  private final Function<ConsoleController, Boolean> job;

  ConsoleCommandV2(String command, Function<ConsoleController, Boolean> job) {
    this.command = command;
    this.job = job;
  }

  public boolean execute(ConsoleController consoleController) {
    return job.apply(consoleController);
  }
}
