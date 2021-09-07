package org.prgrms.kdtspringorder.io.domain;

import java.util.List;

public class Command {

  private final String command;
  private final List<String> options;

  public Command(String command, List<String> options) {
    this.command = command;
    this.options = options;
  }

  public String getName() {
    return command;
  }

  public List<String> getOptions() {
    return options;
  }
}
