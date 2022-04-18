package org.prgms.management.voucher.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private static final int MAX_AMOUNT = 100000;
    private static final int ZERO_AMOUNT = 0;
    private static final int MAX_LENGTH = 100;
    private static final int ZERO_LENGTH = 0;

    private final UUID voucherId;
    private final int amount;
    private final String voucherName;
    private final String voucherType;

    private final LocalDateTime createdAt;

    private FixedAmountVoucher(UUID voucherId, int amount, String voucherName, String voucherType, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherName = voucherName;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    public static FixedAmountVoucher getFixedAmountVoucher(
            UUID voucherId, int amount, String voucherName,
            String voucherType, LocalDateTime createdAt) {
        if (!validate(voucherName, amount)) return null;
        return new FixedAmountVoucher(voucherId, amount, voucherName, voucherType, createdAt);
    }

    private static Boolean validate(String name, int amount) {
        if (name.length() == ZERO_LENGTH) {
            logger.error("바우처 이름은 {}글자를 넘어야 합니다.", ZERO_AMOUNT);
            return false;
        }

        if (name.length() == MAX_LENGTH) {
            logger.error("바우처 이름은 {}글자 이상이면 안됩니다.", MAX_LENGTH);
            return false;
        }

        if (amount == ZERO_AMOUNT) {
            logger.error("할인액은 을{} 넘어야 합니다.", ZERO_AMOUNT);
            return false;
        }

        if (amount > MAX_AMOUNT) {
            logger.error("할인액은 {} 이상이면 안됩니다.", MAX_AMOUNT);
            return false;
        }

        return true;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public String getVoucherType() {
        return this.voucherType;
    }

    @Override
    public String getVoucherName() {
        return this.voucherName;
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
