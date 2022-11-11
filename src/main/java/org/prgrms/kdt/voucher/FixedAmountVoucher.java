package org.prgrms.kdt.voucher;

public class FixedAmountVoucher implements Voucher {
    private static final Long FIXED_AMOUNT_VOUCHER_MIN_VALUE = 0L;

    private final long id;
    private final VoucherAmount amount;

    public FixedAmountVoucher(long id, VoucherAmount amount) {
        validate(amount);
        this.id = id;
        this.amount = amount;
    }

    private void validate(VoucherAmount amount) {
        if (!isValidAmount(amount)) {
            throw new NumberFormatException("Please enter a value greater than or equal " + FIXED_AMOUNT_VOUCHER_MIN_VALUE  + "." + System.lineSeparator());
        }
    }

    private boolean isValidAmount(VoucherAmount voucherAmount) {
        return voucherAmount.isGreaterThan(FIXED_AMOUNT_VOUCHER_MIN_VALUE);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[type]: fixed, [amount]: " + amount.getValue();
    }
}
