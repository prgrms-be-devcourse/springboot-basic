package org.prgrms;

import java.util.stream.Stream;
import org.prgrms.exception.NoSuchMenuTypeException;

public enum MenuType {
  EXIT("exit"),
  CREATE("create"),
  LIST("list");

  private final String menu;

  MenuType(String menu) {
    this.menu = menu;
  }

  public static MenuType of(String inputMenu) {
    return Stream.of(MenuType.values())
        .filter(type -> type.menu.equals(inputMenu))
        .findFirst()
        .orElseThrow(NoSuchMenuTypeException::new);
  }

}
