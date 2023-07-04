package com.prgrms.commandLineApplication.io;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum MenuType {
  EXIT,
  LIST,
  CREATE;

  private static final String ERROR_MESSAGE = "Invalid Menu Type";
  private static final Map<String, MenuType> MENU_TYPES = new HashMap<>();

  static {
    Arrays.stream(values())
            .forEach(menu -> MENU_TYPES.put(menu.name(), menu));
  }

  public static MenuType valueOfType(String menu) {
    return Optional.ofNullable(MENU_TYPES.get(menu.toUpperCase()))
            .orElseThrow(() -> new IllegalArgumentException(ERROR_MESSAGE + " -> " + menu));
  }

}
