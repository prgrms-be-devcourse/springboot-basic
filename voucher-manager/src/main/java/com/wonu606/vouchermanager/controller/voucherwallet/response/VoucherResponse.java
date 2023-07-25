package com.wonu606.vouchermanager.controller.voucherwallet.response;

public class VoucherResponse {

    private final String uuid;
    private final String Type;
    private final Double value;

    public VoucherResponse(String uuid, String type, Double value) {
        this.uuid = uuid;
        Type = type;
        this.value = value;
    }

    public String getUuid() {
        return uuid;
    }

    public String getType() {
        return Type;
    }

    public Double getValue() {
        return value;
    }
}
