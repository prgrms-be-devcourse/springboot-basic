package com.wonu606.vouchermanager.repository.customer.resultset;

public class CustomerCreateResultSet {

    private final Integer taskSuccess;

    public CustomerCreateResultSet(Integer taskSuccess) {
        this.taskSuccess = taskSuccess;
    }

    public Integer getTaskSuccess() {
        return taskSuccess;
    }
}
