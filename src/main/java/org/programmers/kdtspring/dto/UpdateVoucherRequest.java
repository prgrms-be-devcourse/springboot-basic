package org.programmers.kdtspring.dto;

public class UpdateVoucherRequest {
    private String customerId;

    public UpdateVoucherRequest() {
    }

    public UpdateVoucherRequest(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}