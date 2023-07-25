package com.wonu606.vouchermanager.service.voucherwallet.result;

public class WalletAssignResult {

    private final Boolean taskSuccess;

    public WalletAssignResult(Boolean taskSuccess) {
        this.taskSuccess = taskSuccess;
    }

    public Boolean getTaskSuccess() {
        return taskSuccess;
    }
}
