package org.programmers.devcourse.voucher.engine.blacklist;

import java.text.MessageFormat;

public class BlackList {

  private final String name;
  private final String reason;

  public BlackList(String name, String reason) {
    this.name = name;
    this.reason = reason;
  }

  public String getName() {
    return name;
  }

  public String getReason() {
    return reason;
  }

  @Override
  public String toString() {
    return MessageFormat.format("Name : {0}, Reason : {1}", name, reason);
  }
}
