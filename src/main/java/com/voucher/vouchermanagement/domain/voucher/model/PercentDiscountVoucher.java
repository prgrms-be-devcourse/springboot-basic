package com.voucher.vouchermanagement.domain.voucher.model;

import static com.voucher.vouchermanagement.utils.datetime.LocalDateTimeUtils.*;

import java.time.LocalDateTime;
import java.util.UUID;

import com.voucher.vouchermanagement.exception.NotValidValueException;

public class PercentDiscountVoucher implements Voucher {

    private final UUID id;
    private long value;
    private final LocalDateTime createdAt;

    private static final long MAX_PERCENT = 100;
    private static final long MIN_PERCENT = 1;

    public PercentDiscountVoucher(UUID id, Long value, LocalDateTime createdAt) {
        validateValue(value);
        this.id = id;
        this.value = value;
        this.createdAt = toMicrosLocalDateTime(createdAt);
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long)(beforeDiscount * (1 - (value / 100.0)));
    }

    @Override
    public UUID getVoucherId() {
        return id;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "voucher id = " + id + ", percent = " + value + "%, createdAt = " + createdAt;
    }

    private void validateValue(long value) {
        if (value < MIN_PERCENT)
            throw new NotValidValueException("1% 미만일 수 없습니다.");
        if (value > MAX_PERCENT)
            throw new NotValidValueException("100%를 초과할 수 없습니다.");
    }
}
