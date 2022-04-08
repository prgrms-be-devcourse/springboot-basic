package org.programmers.devcourse.voucher.engine;

import java.util.Arrays;
import java.util.Optional;

public enum MenuSelection {
  EXIT("exit"), CREATE("create"), LIST("list"), BLACKLIST("blacklist");

  private final String id;


  MenuSelection(String id) {
    this.id = id;
  }


  public static Optional<MenuSelection> from(String candidate) {
    return Arrays.stream(MenuSelection.values())
        .filter(menuSelection -> menuSelection.id.equals(candidate))
        .findFirst();
  }

}
