package com.example.commandlineapplication.global.io;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Command {

  EXIT,
  CREATE,
  DELETE,
  LIST;

  private static final Map<String, Command> commands = Collections.unmodifiableMap(
      Stream.of(values())
          .collect(Collectors.toMap(Command::getLowerCaseCommand, Function.identity())));

  public static Command find(String inputCommand) {
    return Optional.ofNullable(commands.get(inputCommand))
        .orElseThrow(IllegalArgumentException::new);
//    return Arrays.stream(values())
//        .filter(command -> command.getLowerCaseCommand().equals(inputCommand))
//        .findFirst()
//        .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어입니다. " + inputCommand));
  }

  public String getLowerCaseCommand() {
    return this.name().toLowerCase();
  }
}
