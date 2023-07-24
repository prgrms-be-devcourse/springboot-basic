package com.prgmrs.voucher.controller.mvc.dto;

public class VoucherMvcRemoveRequest {
    private String voucherId;

    public VoucherMvcRemoveRequest() {
        // Do nothing for thymeleaf support purpose
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }
}