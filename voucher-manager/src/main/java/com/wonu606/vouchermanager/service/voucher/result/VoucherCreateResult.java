package com.wonu606.vouchermanager.service.voucher.result;

public class VoucherCreateResult {

    private final Boolean taskSuccess;

    public VoucherCreateResult(Boolean taskSuccess) {
        this.taskSuccess = taskSuccess;
    }

    public Boolean getTaskSuccess() {
        return taskSuccess;
    }
}
