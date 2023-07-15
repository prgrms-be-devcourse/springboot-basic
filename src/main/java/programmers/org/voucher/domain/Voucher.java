package programmers.org.voucher.domain;

import programmers.org.voucher.constant.VoucherType;

import static programmers.org.voucher.exception.ErrorMessage.INVALID_DISCOUNT_RANGE;

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
        switch (type) {
            case FIXED:
                validateFixedAmount(discountAmount);
                break;
            case PERCENT:
                validatePercentAmount(discountAmount);
                break;
        }
    }

    private void validateFixedAmount(int discountAmount) {
        if (discountAmount <= 0 || discountAmount > 100000) {
            throw new IllegalArgumentException(INVALID_DISCOUNT_RANGE.getMessage());
        }
    }

    private void validatePercentAmount(int discountAmount) {
        if (discountAmount <= 0 || discountAmount > 100) {
            throw new IllegalArgumentException(INVALID_DISCOUNT_RANGE.getMessage());
        }
    }
}
