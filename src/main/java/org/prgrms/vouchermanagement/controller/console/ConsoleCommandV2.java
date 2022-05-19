package org.prgrms.vouchermanagement.controller.console;

import java.util.function.Function;

public enum ConsoleCommandV2 {
  LIST(job -> {
    job.processList();
    return true;
  }),
  EXIT(job -> false),
  CREATE(job -> true);

  private final Function<ConsoleController, Boolean> job;

  ConsoleCommandV2(Function<ConsoleController, Boolean> job) {
    this.job = job;
  }

  public boolean execute(ConsoleController consoleController) {
    return job.apply(consoleController);
  }
}
