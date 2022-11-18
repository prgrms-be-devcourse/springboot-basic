package org.prgrms.console;

public class Validator {

  public static long parseNum(String input) {
    try {
      return Long.parseLong(input);
    } catch (NumberFormatException e) {
      throw new RuntimeException("Only numbers can be entered. *current input value: " + input);
    }
  }
}
