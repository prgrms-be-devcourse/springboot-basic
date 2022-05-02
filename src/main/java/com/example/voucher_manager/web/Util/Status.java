package com.example.voucher_manager.web.Util;

public enum Status {
    SUCCESS("success"),
    ERROR("error");

    private final String status;

    private Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
