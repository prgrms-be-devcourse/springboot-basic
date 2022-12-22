package org.prgrms.java.domain.voucher;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.prgrms.java.exception.badrequest.VoucherBadRequestException;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class FixedAmountVoucher extends Voucher {
    private static final long MIN_AMOUNT = 0L;

    @Builder
    public FixedAmountVoucher(UUID voucherId, UUID ownerId, long amount, boolean isUsed, LocalDateTime createdAt, LocalDateTime expiredAt) {
        super(voucherId, ownerId, amount, VoucherType.FIXED, isUsed, createdAt, expiredAt);
        if (amount <= MIN_AMOUNT) throw new VoucherBadRequestException("Voucher discount amount should be positive.");
    }

    @Override
    public long discount(long beforeDiscount) {
        return (beforeDiscount - amount < 0) ? 0 : beforeDiscount - amount;
    }

}
