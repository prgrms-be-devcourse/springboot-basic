package org.prgrms.application.domain.voucher;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(Long voucherId, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("금액은 양수여야 합니다.");
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public Long getVoucherId() {
        return voucherId;
    }

    @Override
    public double discount(double beforeDiscount) {
        double discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }

}
