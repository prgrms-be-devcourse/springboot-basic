package org.devcourse.voucher.domain.voucher;

public class VoucherFactory {

    public static Voucher of(VoucherType type, int amount) {
        return switch (type) {
            case PERCENT, FIX -> discountVoucher(0L, type, amount);
        };
    }

    public static Voucher discountVoucher(long id, VoucherType type, int amount) {
        return switch (type) {
            case PERCENT -> new PercentDiscountVoucher(id, type, amount);
            case FIX -> new FixedAmountVoucher(id, type, amount);
        };
    }
}
