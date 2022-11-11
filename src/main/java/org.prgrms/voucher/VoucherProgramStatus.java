package org.prgrms.voucher;

public class VoucherProgramStatus {

  private boolean runningState;

  public VoucherProgramStatus() {
    this.runningState = true;
  }

  public boolean isRunnable() {
    return runningState;
  }

  public void stop() {
    runningState = false;
  }

}
