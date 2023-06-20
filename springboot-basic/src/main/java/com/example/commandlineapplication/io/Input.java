package com.example.commandlineapplication.io;

import java.util.Scanner;

public class Input {
  private static final Scanner scanner = new Scanner(System.in);

  public String input() {
    return scanner.nextLine();
  }
}
