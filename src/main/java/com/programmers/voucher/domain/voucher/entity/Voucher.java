package com.programmers.voucher.domain.voucher.entity;

import com.programmers.voucher.exception.BadRequestException;

import java.util.UUID;

import static com.programmers.voucher.constant.ErrorCode.INVALID_DISCOUNT_AMOUNT;
import static com.programmers.voucher.constant.ErrorCode.INVALID_DISCOUNT_PERCENT;

public class Voucher {
    private final UUID id;
    private VoucherType type;
    private int amount;

    public Voucher(UUID id, VoucherType type, int amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public static Voucher create(VoucherType type, int amount) {
        validatePositive(amount);
        validatePercent(type, amount);

        return new Voucher(UUID.randomUUID(), type, amount);
    }

    public void update(VoucherType type, int amount) {
        validatePositive(amount);
        validatePercent(type, amount);

        this.type = type;
        this.amount = amount;
    }

    public long discount(long price) {
        return type.discount(price, amount);
    }

    private static void validatePositive(int amount) {
        if (amount <= 0) {
            throw new BadRequestException(INVALID_DISCOUNT_AMOUNT);
        }
    }

    private static void validatePercent(VoucherType type, int amount) {
        if (type == VoucherType.PERCENT && amount > 100) {
            throw new BadRequestException(INVALID_DISCOUNT_PERCENT);
        }
    }

    public UUID getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
