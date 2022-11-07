package org.prgrms.voucherMemory;

public class Generate {
  private static Long newId = 0L;

  public static synchronized Long id() {
    newId += 1;
    return newId;
  }
}
