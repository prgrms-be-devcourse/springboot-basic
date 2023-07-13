package programmers.org.voucher.domain;

import programmers.org.voucher.constant.VoucherType;

import static programmers.org.voucher.exception.ErrorMessage.DISCOUNT_ERROR_MESSAGE;

public class Voucher {
    private Long id;

    private int discountAmount;

    private final VoucherType type;

    public Voucher(int discountAmount, VoucherType type) {
        validateVoucher(discountAmount, type);
        this.discountAmount = discountAmount;
        this.type = type;
    }

    public Voucher(Long id, int discountAmount, VoucherType type) {
        validateVoucher(discountAmount, type);
        this.id = id;
        this.discountAmount = discountAmount;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public VoucherType getType() {
        return type;
    }

    private void validateVoucher(int discountAmount, VoucherType type) {
        if (type.isInvalidAmount(discountAmount)) {
            throw new IllegalArgumentException(DISCOUNT_ERROR_MESSAGE.getMessage());
        }
    }
}
