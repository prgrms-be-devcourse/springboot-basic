package org.prgrms.voucher;

public class VoucherExecution {
  private static boolean flag = true;

  public static boolean isRunnable() {
    return flag;
  }

  public static void stop() {
    flag = false;
  }
}
