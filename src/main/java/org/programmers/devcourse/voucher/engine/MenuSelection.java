package org.programmers.devcourse.voucher.engine;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum MenuSelection {
  EXIT("exit"), CREATE("create"), LIST("list"), BLACKLIST("blacklist");

  private final String id;

  private static final Map<String, MenuSelection> cache = Arrays
      .stream(MenuSelection.values())
      .collect(Collectors.toMap(selection -> selection.id, selection -> selection));

  MenuSelection(String id) {
    this.id = id;
  }

  public static Optional<MenuSelection> from(String candidate) {
    return Optional.ofNullable(cache.get(candidate));
  }

}
