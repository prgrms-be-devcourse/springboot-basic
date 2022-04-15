package com.voucher.vouchermanagement.manager.command;

import java.util.Arrays;

public enum CommandType {
  CREATE("create"), LIST("list"), BLACKLIST("blacklist"), EXIT("exit");

  private String commandName;

  CommandType(String commandName) {
    this.commandName = commandName;
  }

  public String getCommandName() {
    return commandName;
  }

  public static CommandType getCommandTypeByName(String nameInput) {
    return Arrays.stream(CommandType.values())
        .filter(commandType -> commandType.getCommandName().equals(nameInput))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("잘못된 커맨드 입력 입니다."));
  }
}
