package org.prgrms.voucher;

public class VoucherExecution {
  private static boolean runningState = true;

  public static boolean isRunnable() {
    return runningState;
  }

  public static void stop() {
    runningState = false;
  }
}
