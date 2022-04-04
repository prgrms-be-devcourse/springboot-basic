package org.programmers.devcourse.voucher.engine;

import java.util.Arrays;
import java.util.Optional;

public enum Selection {
  EXIT("exit"), CREATE("create"), LIST("list");

  private final String id;


  Selection(String id) {
    this.id = id;
  }


  public static Optional<Selection> from(String candidate) {
    return Arrays.stream(Selection.values()).filter(selection -> selection.id.equals(candidate))
        .findFirst();
  }

}
