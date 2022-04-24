package com.mountain.voucherApp.application.port.in;

public class VoucherCreateDto {
    private int policyId;
    private Long discountAmount;

    public VoucherCreateDto(int policyId, Long discountAmount) {
        this.policyId = policyId;
        this.discountAmount = discountAmount;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }
}
