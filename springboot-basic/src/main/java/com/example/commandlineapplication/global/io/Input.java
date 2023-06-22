package com.example.commandlineapplication.global.io;

import java.util.Scanner;

public class Input {
  private static final Scanner scanner = new Scanner(System.in);

  public String selectMenu() {
    return scanner.nextLine();
  }
}
