package programmers.org.voucher.domain;

import programmers.org.voucher.constant.VoucherType;
import java.util.UUID;

import static programmers.org.voucher.exception.ErrorMessage.DISCOUNT_ERROR_MESSAGE;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;

    private final int discountAmount;

    public FixedAmountVoucher(int discountAmount) {
        validate(discountAmount);
        this.voucherId = UUID.randomUUID();
        this.discountAmount = discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public int getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public void validate(int discount) {
        if (discount < 0) {
            throw new IllegalArgumentException(DISCOUNT_ERROR_MESSAGE.getMessage());
        }
    }
}
