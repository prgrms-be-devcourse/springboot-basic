package org.prgms.management.voucher.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private FixedAmountVoucher(UUID voucherId, int amount, String voucherName, String voucherType) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherName = voucherName;
        this.voucherType = voucherType;
    }

    public static FixedAmountVoucher getFixedAmountVoucher(
            UUID voucherId, int amount, String voucherName, String voucherType) {
        if (!validate(voucherName, amount)) return null;
        return new FixedAmountVoucher(voucherId, amount, voucherName, voucherType);
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
        return voucherId;
    }

    @Override
    public String getVoucherType() {
        return voucherType;
    }

    @Override
    public String getVoucherName() {
        return voucherName;
    }

    @Override
    public long getDiscountNum() {
        return amount;
    }
}
