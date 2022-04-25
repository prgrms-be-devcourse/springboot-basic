package org.prgms.management.voucher.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(PercentAmountVoucher.class);
    private static final int MAX_PERCENT = 100;
    private static final int MIN_PERCENT = 0;
    private static final int MAX_LENGTH = 50;
    private static final int MIN_LENGTH = 0;

    private final UUID voucherId;
    private int percent;
    private String name;
    private final String type;

    private final LocalDateTime createdAt;

    private PercentAmountVoucher(UUID voucherId, int percent, String name, String type, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
    }

    public static PercentAmountVoucher getPercentAmountVoucher(
            UUID voucherId, int percent, String name,
            String type, LocalDateTime createdAt) {
        if (!validateName(name) || !validatePercent(percent)) {
            return null;
        }

        return new PercentAmountVoucher(
                voucherId, percent, name, type, createdAt);
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

    private static boolean validatePercent(int percent) {
        if (percent <= MIN_PERCENT) {
            logger.error("할인율은 을{} 넘어야 합니다.", MIN_PERCENT);
            return false;
        }

        if (percent > MAX_PERCENT) {
            logger.error("할인율은 {} 이상이면 안됩니다.", MAX_PERCENT);
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
        if (validatePercent(discountNum)) {
            this.percent = discountNum;
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
        return this.percent;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
