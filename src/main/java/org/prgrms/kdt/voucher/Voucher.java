package org.prgrms.kdt.voucher;

public interface Voucher {

    static Voucher newInstance(VoucherType type, VoucherAmount amount) {
        return switch (type) {
            case FIXED -> new FixedAmountVoucher(VoucherId.increase(), amount);
            case PERCENT -> new PercentDiscountVoucher(VoucherId.increase(), amount);
        };
    }

    long getId();

    String getType();

    String getAmount();
}
