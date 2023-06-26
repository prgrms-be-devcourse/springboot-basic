package org.devcourse.voucher.domain.voucher;

public class VoucherFactory {

    public static Voucher of(VoucherType type, int amount) {
        validateType(type);
        return switch (type) {
            case PERCENT, FIX -> discountVoucher(0L, type, amount);
            default -> throw new RuntimeException("사용 불가능한 타입입니다");
        };
    }

    private static Voucher discountVoucher(long id, VoucherType type, int amount) {
        return switch (type) {
            case PERCENT -> new PercentDiscountVoucher(id, type, amount);
            case FIX -> new FixedAmountVoucher(id, type, amount);
            default -> throw new RuntimeException("사용 불가능한 타입입니다");
        };
    }

    private static void validateType(VoucherType type) {
        if (type == null) {
            throw new RuntimeException("타입을 입력하세요");
        }
    }
}
