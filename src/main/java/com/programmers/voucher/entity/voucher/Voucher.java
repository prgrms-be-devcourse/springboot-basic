package com.programmers.voucher.entity.voucher;

import com.programmers.voucher.constant.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Voucher {
    private static final Logger logger = LoggerFactory.getLogger(Voucher.class);
    private UUID id;
    private VoucherType type;
    private int amount;

    public Voucher(UUID id, VoucherType type, int amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public static Voucher create(VoucherType type, int amount) {
        return new Voucher(UUID.randomUUID(), type, amount);
    }

    public void update(int amount) {
        if (type == VoucherType.PERCENT) {
            validatePercent(amount);
        }
        this.amount = amount;
    }

    private void validatePercent(int amount) {
        if (amount > 100) {
            logger.error("{} => {}", ErrorMessage.INVALID_DISCOUNT_PERCENT, amount);
            throw new IllegalArgumentException(ErrorMessage.INVALID_DISCOUNT_PERCENT);
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
