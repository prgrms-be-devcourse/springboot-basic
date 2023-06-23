package com.example.commandlineapplication.global.io;

import java.util.Scanner;

public class Input {
  private static final Scanner scanner = new Scanner(System.in);

  public String selectOption() {
    return scanner.nextLine();
  }

  public Integer getDiscount() {
    return Integer.valueOf(scanner.nextLine());
  }
}
