package com.programmers.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final Logger log = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    private final UUID voucherId;
    private final String voucherName;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, String voucherName, long percent) {
        log.warn("The voucher input will be validated. voucher type = {}", PercentDiscountVoucher.class.getName());

        if (voucherName.isEmpty() || percent < 1 || percent > 100) {
            log.error("Empty Input or the invalid voucher input found. voucher type = {}", PercentDiscountVoucher.class.getName());

            throw new IllegalArgumentException();
        }

        this.voucherId = voucherId;
        this.voucherName = voucherName;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getVoucherName() {
        return voucherName;
    }

    @Override
    public long getDiscountValue() {
        return percent;
    }

    @Override
    public String toString() {
        return "[ Voucher Type = Percent Discount Voucher" +
                ", Id = " + voucherId +
                ", discount percent = " + percent +
                ", voucher name = " + voucherName + " ]";
    }
}