package com.prgrms.commandLineApplication.io;

public enum MenuType {
  EXIT,
  LIST,
  CREATE;

  private static final String ERROR_MESSAGE = "Invalid Menu Type";

  public static MenuType valueOfType(String menu) {
    try {
      return MenuType.valueOf(menu.toUpperCase());
    } catch (Exception e) {
      throw new IllegalArgumentException(ERROR_MESSAGE);
    }
  }

}
