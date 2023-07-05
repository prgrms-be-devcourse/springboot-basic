package org.devcourse.voucher.controller.console.dto;

public enum Status {
    RUNNING,
    FINISH;

    public boolean isRunning() {
        return !this.equals(FINISH);
    }
}
