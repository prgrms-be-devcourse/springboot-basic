package com.prgms.kdtspringorder.domain.model.voucher;

import java.text.MessageFormat;
import java.util.UUID;

import com.prgms.kdtspringorder.adapter.exception.InvalidDiscountException;
import com.prgms.kdtspringorder.ui.message.ErrorMessage;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long discountAmount;
    private boolean isUsed = false;

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    @Override
    public long discount(long beforeDiscountAmount) {
        long discounted = beforeDiscountAmount - discountAmount;
        if (discounted < 0) {
            throw new InvalidDiscountException(
                MessageFormat.format("{0}\n원가: {1}, 바우처 할인 금액: {2}", ErrorMessage.INVALID_DISCOUNT_AMOUNT.getMessage(),
                    beforeDiscountAmount, discountAmount));
        }
        return discounted;
    }

    @Override
    public void useVoucher() {
        isUsed = !isUsed;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount() {
        return discountAmount;
    }
}
