package org.prgrms.application.domain.voucher;

public class VoucherEntity {
    private Long voucherId;
    private VoucherType voucherType;
    private double discountAmount;

    public VoucherEntity(Long voucherId, String voucherType, double discountAmount) {
        this.voucherId = voucherId;
        this.voucherType = convertToVoucherType(voucherType);
        this.discountAmount = discountAmount;
    }

    private VoucherType convertToVoucherType(String voucherType) {
        switch (voucherType) {
            case "FIXED":
                return VoucherType.FIXED;
            case "PERCENT":
                return VoucherType.PERCENT;
            default:
                throw new IllegalArgumentException("Unknown voucher type: " + voucherType);
        }
    }

    public Voucher toDomainObject(){
        switch (this.voucherType){
            case FIXED:
                return new FixedAmountVoucher(this.voucherId,this.voucherType, this.discountAmount);

            case PERCENT:
                return new PercentAmountVoucher(this.voucherId,this.voucherType, this.discountAmount);

            default:
                throw new IllegalArgumentException("알 수 없는 voucherType입니다.");
        }
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}
