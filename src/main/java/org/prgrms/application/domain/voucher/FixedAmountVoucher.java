package org.prgrms.application.domain.voucher;

public class FixedAmountVoucher extends Voucher {

    private double fixedAmount;

    public FixedAmountVoucher(Long voucherId, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("금액은 양수여야 합니다.");
        this.voucherId = voucherId;
        this.fixedAmount = amount;
    }

    public double getFixedAmount(){
        return fixedAmount;
    }

    @Override
    public Long getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public double discount(double beforeDiscount) {
        double discountedAmount = beforeDiscount - fixedAmount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }

}
