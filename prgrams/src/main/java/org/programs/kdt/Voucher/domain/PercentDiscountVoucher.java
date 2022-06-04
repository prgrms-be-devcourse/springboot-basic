package org.programs.kdt.Voucher.domain;

import lombok.Getter;
import lombok.ToString;
import org.programs.kdt.Exception.ErrorCode;
import org.programs.kdt.Exception.InvalidValueException;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;

    private long value;

    private final LocalDateTime createdAt;

    public PercentDiscountVoucher(UUID voucherId, long value) {
        validation(value);
        this.voucherId = voucherId;
        this.value = value;
        this.createdAt = LocalDateTime.now();
    }

    public PercentDiscountVoucher(UUID voucherId, long value, LocalDateTime createdAt) {
        validation(value);
        this.voucherId = voucherId;
        this.value = value;
        this.createdAt = createdAt;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (100 - value) / 100;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public void validation (long value) {
        if (value <= 0 || value > getMaxValue()) {
            throw new InvalidValueException(ErrorCode.INVALID_VOUCHER_VALUE);
        }
    }

    @Override
    public long getMaxValue() {
        return 100;
    }

    @Override
    public void changeValue(Long value) {
        validation(value);
        this.value = value;
    }


}
