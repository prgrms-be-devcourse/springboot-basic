package com.voucher.vouchermanagement.domain.voucher.model;

import static com.voucher.vouchermanagement.utils.datetime.LocalDateTimeUtils.*;

import java.time.LocalDateTime;
import java.util.UUID;

import com.voucher.vouchermanagement.exception.NotValidValueException;

public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private long value;
    private final LocalDateTime createdAt;

    private static final long MIN_PERCENT = 1;

    public FixedAmountVoucher(UUID id, Long value, LocalDateTime createdAt) {
        validateValue(value);
        this.id = id;
        this.value = value;
        this.createdAt = toMicrosLocalDateTime(createdAt);
    }

    @Override
    public long discount(long beforeDiscount) {
        if (beforeDiscount - value < 0) {
            return 0;
        }
        return beforeDiscount - value;
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

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "voucher id = " + id + ", amount = " + value + ", createdAt = " + createdAt;
    }

    private void validateValue(long value) {
        if (value < MIN_PERCENT) {
            throw new NotValidValueException("할인 금액이 1원 미만일 수 없습니다.");
        }
    }
}
