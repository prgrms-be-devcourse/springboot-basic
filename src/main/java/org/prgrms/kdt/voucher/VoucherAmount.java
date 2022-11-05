package org.prgrms.kdt.voucher;

public class VoucherAmount {
    private Long value;

    public VoucherAmount(String amount) {
        this.value = Long.parseLong(amount);
    }

    public Long getValue() {
        return value;
    }

    public boolean isValidFixedVoucherAmount() {
        return isGreaterThanEqual(0L) && isLessThanEqual(100L);
    }

    private boolean isGreaterThanEqual(Long value) {
        return this.value >= value;
    }

    private boolean isLessThanEqual(Long value) {
        return this.value <= value;
    }

    public boolean isValidPercentDiscountAmount() {
        return isGreaterThan(0L);
    }

    private boolean isGreaterThan(long value) {
        return this.value > value;
    }
}
