package com.example.commandlineapplication.io;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Command {
  EXIT("exit", "Type exit to exit the program."),
  CREATE("create", "Type create to create a new voucher."),
  LIST("list", "Type list to list all vouchers.");

  private final String command;
  private final String message;

  Command(String command, String message) {
    this.command = command;
    this.message = message;
  }

  public static List<String> getMessages() {
    return Arrays.stream(Command.values())
        .map(Command::toString)
        .collect(Collectors.toList());
  }

  @Override
  public String toString() {
    return this.command;
  }
}
