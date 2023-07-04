package com.programmers.domain.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final Logger log = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    private final UUID voucherId;
    private final String voucherName;
    private final long percent;
    private final VoucherType voucherType;

    public PercentDiscountVoucher(UUID voucherId, String voucherName, long percent) {
        checkVoucherInput(voucherName, percent);

        this.voucherId = voucherId;
        this.voucherName = voucherName;
        this.percent = percent;
        this.voucherType = VoucherType.PercentDiscountVoucher;
    }

    private void checkVoucherInput(String voucherName, long percent) {
        if (voucherName.isEmpty() || percent < 1 || percent > 100) {
            log.error("Empty Input or the invalid voucher input found. voucher type = {}", PercentDiscountVoucher.class.getName());

            throw new IllegalArgumentException();
        }
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
    public long getVoucherValue() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long)(beforeDiscount * ((100 - percent) / 100.0));
    }

    @Override
    public String toString() {
        return "[ Voucher Type = Percent Discount Voucher" +
                ", Id = " + voucherId +
                ", discount percent = " + percent +
                ", voucher name = " + voucherName + " ]";
    }
}