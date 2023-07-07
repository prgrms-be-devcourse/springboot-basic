package com.prgrms.commandLineApplication.io;

import java.io.IOException;

public enum MenuType {
  EXIT,
  LIST,
  CREATE;

  private static final String MENU_TYPE_ERROR = "Invalid Menu Type";

  public static MenuType valueOfType(String menu) throws IOException {
    try {
      return MenuType.valueOf(menu.toUpperCase());
    } catch (Exception e) {
      throw new IOException(MENU_TYPE_ERROR);
    }
  }

}
