package com.programmers.springbootbasic.voucher.domain;

import com.programmers.springbootbasic.exception.InvalidRequestValueException;
import com.programmers.springbootbasic.exception.InvalidVoucherValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final Logger log = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    private final UUID voucherId;
    private final String voucherName;
    private final long percent;
    private final VoucherType voucherType;
    private final Optional<UUID> customerId;

    public PercentDiscountVoucher(UUID voucherId, String voucherName, long percent, Optional<UUID> customerId) {
        checkVoucherValue(voucherName, percent);

        this.voucherId = voucherId;
        this.voucherName = voucherName;
        this.percent = percent;
        this.voucherType = VoucherType.PercentDiscountVoucher;
        this.customerId = customerId;
    }

    public PercentDiscountVoucher(UUID voucherId, String voucherName, long percent) {
        checkVoucherValue(voucherName, percent);

        this.voucherId = voucherId;
        this.voucherName = voucherName;
        this.percent = percent;
        this.voucherType = VoucherType.PercentDiscountVoucher;
        this.customerId = Optional.empty();
    }

    private void checkVoucherValue(String voucherName, long percent) {
        if (voucherName.isEmpty()) {
            log.error("The voucher name not found. voucher type = {}", PercentDiscountVoucher.class.getName());
            throw new InvalidRequestValueException("[ERROR] voucher 이름이 비었습니다.");
        }

        if (percent < 1 || percent > 100) {
            log.error("The invalid voucher percent found. voucher type = {}", PercentDiscountVoucher.class.getName());
            throw new InvalidVoucherValueException("[ERROR] percent 값이 유효하지 않습니다.");
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
    public Optional<UUID> getCustomerId() {
        return customerId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long)(beforeDiscount * ((100 - percent) / 100.0));
    }

    @Override
    public String toString() {
        return customerId.map(
                uuid -> "[ voucher type = Percent Discount Voucher" +
                        ", voucher id = " + voucherId +
                        ", discount percent = " + percent +
                        ", voucher name = " + voucherName +
                        ", customer id = " + uuid +
                        " ]"
        ).orElseGet(() ->
                "[ voucher type = Percent Discount Voucher" +
                        ", voucher id = " + voucherId +
                        ", discount percent = " + percent +
                        ", voucher name = " + voucherName +
                        ", customer id = null ]");
    }
}