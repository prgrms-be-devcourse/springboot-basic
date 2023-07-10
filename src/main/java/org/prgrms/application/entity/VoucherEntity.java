package org.prgrms.application.entity;

public class VoucherEntity {
    private Long voucherId;
    private String voucherType;
    private double discountAmount;

    public VoucherEntity(Long voucherId, String voucherType, double discountAmount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void changeDiscountAmount(double discountAmount){
        this.discountAmount = discountAmount;
    }
}
