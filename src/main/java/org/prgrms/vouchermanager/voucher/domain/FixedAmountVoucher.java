package org.prgrms.vouchermanager.voucher.domain;

public class FixedAmountVoucher extends AbstractVoucher {

    private final long MAX_VOUCHER_AMOUNT = 10000;
    private final long amount;

    public FixedAmountVoucher(long amount) {
        super(VoucherType.FIXED);

        if (amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount > MAX_VOUCHER_AMOUNT)
            throw new IllegalArgumentException("Amount should be less than %d".formatted(MAX_VOUCHER_AMOUNT));

        this.amount = amount;
    }

    @Override
    public Long discount(long beforeDiscount) {
        return beforeDiscount - amount < 0 ? 0 : beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + getVoucherId() +
                ", voucherType=" + getVoucherType() +
                ", amount=" + amount +
                '}';
    }
}
