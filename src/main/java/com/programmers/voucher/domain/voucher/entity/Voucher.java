package com.programmers.voucher.domain.voucher.entity;

import com.programmers.voucher.exception.BadRequestException;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

import static com.programmers.voucher.constant.ErrorCode.INVALID_DISCOUNT_PERCENT;

@Getter
public class Voucher {
    private final UUID id;
    private VoucherType type;
    private int amount;

    @Builder
    private Voucher(UUID id, VoucherType type, int amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public static Voucher create(VoucherType type, int amount) {
        validatePercent(type, amount);
        return new Voucher(UUID.randomUUID(), type, amount);
    }

    public void update(VoucherType type, int amount) {
        validatePercent(type, amount);

        this.type = type;
        this.amount = amount;
    }

    public long discount(long price) {
        return type.discount(price, amount);
    }

    private static void validatePercent(VoucherType type, int amount) {
        if (type == VoucherType.PERCENT && amount > 100) {
            throw new BadRequestException(INVALID_DISCOUNT_PERCENT);
        }
    }
}
