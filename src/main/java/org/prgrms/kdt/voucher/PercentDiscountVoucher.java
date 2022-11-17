package org.prgrms.kdt.voucher;

public class PercentDiscountVoucher implements Voucher {
    private static final Long PERCENT_DISCOUNT_VOUCHER_MIN_VALUE = 0L;
    private static final Long PERCENT_DISCOUNT_VOUCHER_MAX_VALUE = 100L;
    private static final VoucherType TYPE = VoucherType.PERCENT;

    private final long id;
    private final VoucherAmount amount;

    public PercentDiscountVoucher(long id, VoucherAmount amount) {
        validate(amount);
        this.id = id;
        this.amount = amount;
    }

    private void validate(VoucherAmount amount) {
        if (!isValidAmount(amount)) {
            throw new NumberFormatException("Please enter a value between " + PERCENT_DISCOUNT_VOUCHER_MIN_VALUE + " and " + PERCENT_DISCOUNT_VOUCHER_MAX_VALUE + "." + System.lineSeparator());
        }
    }

    private boolean isValidAmount(VoucherAmount voucherAmount) {
        return voucherAmount.isGreaterThanEqual(PERCENT_DISCOUNT_VOUCHER_MIN_VALUE) && voucherAmount.isLessThanEqual(PERCENT_DISCOUNT_VOUCHER_MAX_VALUE);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getType() {
        return TYPE.getType().toUpperCase();
    }

    @Override
    public String getAmount() {
        return String.valueOf(amount.getValue());
    }

    @Override
    public String toString() {
        return "[type]: percent, [amount]: " + amount.getValue() + "%";
    }
}
