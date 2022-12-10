package org.prgrms.java.domain.voucher;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.prgrms.java.exception.badrequest.VoucherBadRequestException;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PercentDiscountVoucher extends Voucher {
    private static final long MAX_AMOUNT = 100L;
    private static final long MIN_AMOUNT = 0L;

    @Builder
    public PercentDiscountVoucher(UUID voucherId, UUID ownerId, long amount, boolean isUsed, LocalDateTime createdAt, LocalDateTime expiredAt) {
        super(voucherId, ownerId, amount, "PERCENT", isUsed, createdAt, expiredAt);
        if (amount <= MIN_AMOUNT) throw new VoucherBadRequestException("Voucher discount percent should be positive.");
        if (amount > MAX_AMOUNT) throw new VoucherBadRequestException("Voucher discount percent cannot be bigger than 100.");
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (amount / 100);
    }

}
