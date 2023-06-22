package com.example.commandlineapplication.global.io;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Command {
  EXIT,
  CREATE,
  LIST;

  public static Command from(String inputCommand) {
    return Arrays.stream(Command.values())
        .filter(command -> command.getLowerCaseCommand().equals(inputCommand))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어입니다." + inputCommand));
  }

  public String getLowerCaseCommand() {
    return this.name().toLowerCase();
  }
}
