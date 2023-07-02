package org.prgrms.application.domain.voucher;

public class FixedAmountVoucher extends Voucher {

    private double fixedAmount;

    public FixedAmountVoucher(Long voucherId,VoucherType voucherType, double amount) {
        validatePositive(amount);
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.fixedAmount = amount;
    }

    private static void validatePositive(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("금액은 양수여야 합니다.");
    }

    public double getFixedAmount(){

        return fixedAmount;
    }

    public void changeFixedAmount(double fixedAmount){
        validatePositive(fixedAmount);
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
        validatePositive(discountedAmount);
        return discountedAmount;
    }

}
