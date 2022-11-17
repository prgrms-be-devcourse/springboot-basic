package org.prgrms.kdt.voucher;

public interface Voucher {

    static Voucher newInstance(VoucherType type, VoucherAmount amount) {
        return switch (type) {
            case FIXED -> new FixedAmountVoucher(VoucherId.increase(), amount);
            case PERCENT -> new PercentDiscountVoucher(VoucherId.increase(), amount);
        };
    }

    static Voucher getInstance(long id, VoucherType type, VoucherAmount amount) {
        return switch (type) {
            case FIXED -> new FixedAmountVoucher(id, amount);
            case PERCENT -> new PercentDiscountVoucher(id, amount);
        };
    }

    long getId();

    VoucherType getType();

    VoucherAmount getAmount();


}
