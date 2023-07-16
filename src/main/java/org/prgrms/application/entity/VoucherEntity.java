package org.prgrms.application.entity;


import org.prgrms.application.domain.voucher.Voucher;
import org.prgrms.application.domain.voucher.VoucherType;

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

    public Voucher toDomain(){ // 수정 변환해주는 것을 독립,
        VoucherType voucherType = VoucherType.findBySelection(this.voucherType);

        return new Voucher(this.voucherId, voucherType, this.discountAmount);
    }
}
