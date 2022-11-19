package org.prgrms.kdt.voucher;

public interface Voucher {

    static Voucher newInstance(VoucherType type, VoucherAmount amount) {
        return switch (type) {
            case FIXED -> new FixedAmountVoucher(amount);
            case PERCENT -> new PercentDiscountVoucher(amount);
        };
    }

    static Voucher from(long id, VoucherType type, VoucherAmount amount) {
        return switch (type) {
            case FIXED -> new FixedAmountVoucher(id, amount);
            case PERCENT -> new PercentDiscountVoucher(id, amount);
        };
    }

    long getId();

    VoucherType getType();

    VoucherAmount getAmount();
}
