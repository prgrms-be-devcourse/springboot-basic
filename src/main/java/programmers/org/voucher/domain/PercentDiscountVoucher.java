package programmers.org.voucher.domain;

import programmers.org.voucher.constant.VoucherType;

import java.util.UUID;

import static programmers.org.voucher.exception.ErrorMessage.DISCOUNT_ERROR_MESSAGE;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;

    private final int discountRate;

    public PercentDiscountVoucher(UUID voucherId, int discountRate) {
        validate(discountRate);
        this.voucherId = voucherId;
        this.discountRate = discountRate;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public int getDiscountAmount() {
        return discountRate;
    }

    @Override
    public void validate(int discount) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException(DISCOUNT_ERROR_MESSAGE.getMessage());
        }
    }
}
