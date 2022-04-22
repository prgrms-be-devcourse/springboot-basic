package org.prgms.management.voucher.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(PercentAmountVoucher.class);
    private static final int MAX_AMOUNT = 100;
    private static final int ZERO_AMOUNT = 0;
    private static final int MAX_LENGTH = 100;
    private static final int ZERO_LENGTH = 0;

    private final UUID voucherId;
    private final int percent;
    private final String voucherName;
    private final String voucherType;

    private PercentAmountVoucher(UUID voucherId, int percent, String voucherName, String voucherType) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherName = voucherName;
        this.voucherType = voucherType;
    }

    public static PercentAmountVoucher getPercentAmountVoucher(
            UUID voucherId, int amount, String voucherName, String voucherType) {
        if (!validate(voucherName, amount)) return null;
        return new PercentAmountVoucher(voucherId, amount, voucherName, voucherType);
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
            logger.error("할인율은 을{} 넘어야 합니다.", ZERO_AMOUNT);
            return false;
        }

        if (amount > MAX_AMOUNT) {
            logger.error("할인율은 {} 이상이면 안됩니다.", MAX_AMOUNT);
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
        return percent;
    }
}
