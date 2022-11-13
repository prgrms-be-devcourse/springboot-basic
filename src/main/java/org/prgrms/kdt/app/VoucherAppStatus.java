package org.prgrms.kdt.app;


public class VoucherAppStatus {
    private boolean status = true;

    public boolean isRunning() {
        return status;
    }

    public void quit() {
        this.status = false;
    }
}