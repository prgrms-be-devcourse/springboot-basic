package com.example.voucher_manager.domain.voucher;

public enum VoucherRequestParams {
    TYPE("type"),
    START("start"),
    END("end");

    private final String key;
    private VoucherRequestParams(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
