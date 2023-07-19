package com.wonu606.vouchermanager.service.customer.result;

public class CustomerCreateResult {

    private final boolean taskSuccess;

    public CustomerCreateResult(boolean taskSuccess) {
        this.taskSuccess = taskSuccess;
    }

    public boolean isTaskSuccess() {
        return taskSuccess;
    }
}
