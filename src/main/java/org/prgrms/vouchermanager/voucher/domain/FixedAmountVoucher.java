package org.prgrms.vouchermanager.voucher.domain;

public class FixedAmountVoucher extends AbstractVoucher {
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final long amount;

    public FixedAmountVoucher(long amount) {
        super(VoucherType.FIXED);
        if (amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount > MAX_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("Amount should be less than %d".formatted(MAX_VOUCHER_AMOUNT));
        }
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount < 0 ? 0 : beforeDiscount - amount;
    }

    @Override
    public String toString() {
        //TODO : 후에 출력 양식 정하면 StringBuilder나 Message Format으로 바꿀 것
        return "FixedAmountVoucher{" +
                "voucherId=" + getVoucherId() +
                "voucherType=" + getVoucherType() +
                "amount=" + amount +
                '}';
    }
}
