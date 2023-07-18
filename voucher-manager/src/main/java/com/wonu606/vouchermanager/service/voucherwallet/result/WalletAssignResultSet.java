package com.wonu606.vouchermanager.service.voucherwallet.result;

public class WalletAssignResultSet {

    private final Boolean taskSuccess;

    public WalletAssignResultSet(Boolean taskSuccess) {
        this.taskSuccess = taskSuccess;
    }

    public Boolean getTaskSuccess() {
        return taskSuccess;
    }
}
