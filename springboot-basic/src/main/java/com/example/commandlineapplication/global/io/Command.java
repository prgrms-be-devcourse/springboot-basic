package com.example.commandlineapplication.global.io;

import java.util.Arrays;

public enum Command {
  EXIT,
  CREATE,
  LIST;

  public static Command of(String inputCommand) {
    return Arrays.stream(values())
        .filter(command -> command.getLowerCaseCommand().equals(inputCommand))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어입니다." + inputCommand));
  }

  public String getLowerCaseCommand() {
    return this.name().toLowerCase();
  }
}
