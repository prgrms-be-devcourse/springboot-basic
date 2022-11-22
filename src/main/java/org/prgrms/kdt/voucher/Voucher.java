package org.prgrms.kdt.voucher;

public interface Voucher {

    static Voucher newInstance(VoucherType type, VoucherAmount amount) {
        return switch (type) {
            case FIXED -> new FixedAmountVoucher(amount);
            case PERCENT -> new PercentDiscountVoucher(amount);
            default -> throw new IllegalArgumentException("Unknown Type. [argument type]: " + type.getType());
        };
    }

    static Voucher from(long id, VoucherType type, VoucherAmount amount) {
        return switch (type) {
            case FIXED -> new FixedAmountVoucher(id, amount);
            case PERCENT -> new PercentDiscountVoucher(id, amount);
            default -> throw new IllegalArgumentException("Unknown Type. [argument type]: " + type.getType());
        };
    }

    long getId();

    VoucherType getType();

    VoucherAmount getAmount();
}
