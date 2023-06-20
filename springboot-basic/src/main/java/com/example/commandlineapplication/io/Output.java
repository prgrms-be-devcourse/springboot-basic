package com.example.commandlineapplication.io;

public class Output {
  private static final String menu = "=== Voucher Program ===\nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.";

  public void printMenu() {
    System.out.println(menu);
  }
}
