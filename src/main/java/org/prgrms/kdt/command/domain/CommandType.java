package org.prgrms.kdt.command.domain;

public enum CommandType {
  BLACKLIST,
  CREATE,
  ERROR,
  EXIT,
  INIT,
  LIST;

  public static CommandType of(String command) {
    try {
      return CommandType.valueOf(command.toUpperCase());
    } catch (Exception e) {
      return CommandType.ERROR;
    }
  }
}