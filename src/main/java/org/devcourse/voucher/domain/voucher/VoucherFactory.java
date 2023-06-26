package org.devcourse.voucher.domain.voucher;

public class VoucherFactory {

    public static Voucher discountVoucher(long id, VoucherType type, int amount) {
        validateType(type);
        return new DiscountVoucher(id, type, amount);
    }

    private static void validateType(VoucherType type) {
        if (type == null) {
            throw new RuntimeException("타입을 입력하세요");
        }
    }
}
