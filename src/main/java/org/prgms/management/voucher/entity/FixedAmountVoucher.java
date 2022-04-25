package org.prgms.management.voucher.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private static final int MAX_AMOUNT = 100000;
    private static final int MIN_AMOUNT = 0;
    private static final int MAX_LENGTH = 50;
    private static final int MIN_LENGTH = 0;

    private final UUID voucherId;
    private int amount;
    private String name;
    private final String type;

    private final LocalDateTime createdAt;

    private FixedAmountVoucher(UUID voucherId, int amount, String name, String type, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
    }

    public static FixedAmountVoucher getFixedAmountVoucher(
            UUID voucherId, int amount, String name,
            String type, LocalDateTime createdAt) {
        if (!validateName(name) || !validateAmount(amount)) {
            return null;
        }

        return new FixedAmountVoucher(
                voucherId, amount, name, type, createdAt);
    }

    private static boolean validateName(String name) {
        if (name.length() == MIN_LENGTH) {
            logger.error("바우처 이름은 {}글자를 넘어야 합니다.", MIN_LENGTH);
            return false;
        }

        if (name.length() == MAX_LENGTH) {
            logger.error("바우처 이름은 {}글자 이상이면 안됩니다.", MAX_LENGTH);
            return false;
        }

        return true;
    }

    private static boolean validateAmount(int amount) {
        if (amount <= MIN_AMOUNT) {
            logger.error("할인액은 을{} 넘어야 합니다.", MIN_AMOUNT);
            return false;
        }

        if (amount > MAX_AMOUNT) {
            logger.error("할인액은 {} 이상이면 안됩니다.", MAX_AMOUNT);
            return false;
        }

        return true;
    }

    @Override
    public void changeName(String name) {
        if (validateName(name)) {
            this.name = name;
        }
    }

    @Override
    public void changeDiscountNum(int discountNum) {
        if (validateAmount(discountNum)) {
            this.amount = discountNum;
        }
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getDiscountNum() {
        return this.amount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
