package org.prgrms.console;

public class Validator {

  public static int parseNum(String input) {
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      throw new RuntimeException("Only numbers can be entered. *current input value: " + input);
    }
  }
}
