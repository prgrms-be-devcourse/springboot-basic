package org.prgms.management.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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

        return new FixedAmountVoucher(voucherId, amount, voucherName, voucherType);
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
