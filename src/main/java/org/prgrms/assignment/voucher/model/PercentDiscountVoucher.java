package org.prgrms.assignment.voucher.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.prgrms.assignment.voucher.exception.ErrorCode;
import org.prgrms.assignment.voucher.exception.GlobalCustomException;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final int MIN_VOUCHER_PERCENT = 1;
    private static final long MAX_VOUCHER_PERCENT = 100;
    private static final String PERCENT_ERROR_MESSAGE = "Percent out of range";
    private static final String EXPIRE_DATE_ERROR_MESSAGE = "Expiredate out of range";


    private final LocalDateTime createdAt;
    private final UUID voucherId;

    @Max(value = MAX_VOUCHER_PERCENT, message = PERCENT_ERROR_MESSAGE)
    @Min(value = MIN_VOUCHER_PERCENT, message = PERCENT_ERROR_MESSAGE)
    private long percent;

    @Future(message = EXPIRE_DATE_ERROR_MESSAGE)
    private LocalDateTime expireDate;

    public PercentDiscountVoucher(UUID voucherId, long percent, LocalDateTime createdAt, LocalDateTime expireDate) {
        checkValid(percent);
        this.voucherId = voucherId;
        this.percent = percent;
        this.createdAt = createdAt;
        this.expireDate = expireDate;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long getBenefit() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    private void checkValid(long percent) {
        if(percent < MIN_VOUCHER_PERCENT || percent >= MAX_VOUCHER_PERCENT) {
            throw new GlobalCustomException(ErrorCode.PERCENT_ERROR);
        }
    }
}
