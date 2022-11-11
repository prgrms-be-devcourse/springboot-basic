package org.prgrms.kdt.app;


public class VoucherControllerStatus {
    private boolean status = true;

    public boolean isRunning() {
        return status;
    }

    public void quit() {
        this.status = false;
    }
}