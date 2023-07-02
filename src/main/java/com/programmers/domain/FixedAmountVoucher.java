package com.programmers.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private static final Logger log = LoggerFactory.getLogger(FixedAmountVoucher.class);

    private final UUID voucherId;
    private final String voucherName;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, String voucherName, long amount) {
        checkVoucherInput(voucherName, amount);

        this.voucherId = voucherId;
        this.voucherName = voucherName;
        this.amount = amount;
    }

    private void checkVoucherInput(String voucherName, long amount) {
        if (voucherName.isEmpty() || amount < 0) {
            log.error("Empty Input or the invalid voucher input found. voucher type = {}", FixedAmountVoucher.class.getName());

            throw new IllegalArgumentException();
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "[ Voucher Type = Fixed Amount Voucher" +
                ", Id = " + voucherId +
                ", discount amount = " + amount +
                ", voucher name = " + voucherName + " ]";
    }
}