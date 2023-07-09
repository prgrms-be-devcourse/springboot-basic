package com.programmers.voucher.domain;

import com.programmers.exception.InvalidRequestValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private static final Logger log = LoggerFactory.getLogger(FixedAmountVoucher.class);

    private final UUID voucherId;
    private final String voucherName;
    private final long amount;
    private final VoucherType voucherType;

    public FixedAmountVoucher(UUID voucherId, String voucherName, long amount) {
        checkVoucherValue(voucherName, amount);

        this.voucherId = voucherId;
        this.voucherName = voucherName;
        this.amount = amount;
        this.voucherType = VoucherType.FixedAmountVoucher;
    }

    private void checkVoucherValue(String voucherName, long amount) {
        if (voucherName.isEmpty()) {
            log.error("The voucher name not found. voucher type = {}", FixedAmountVoucher.class.getName());
            throw new InvalidRequestValueException("[ERROR] voucher 이름이 비었습니다.");
        }

        if (amount < 0) {
            log.error("The invalid voucher amount found. voucher type = {}", FixedAmountVoucher.class.getName());
            throw new InvalidRequestValueException("[ERROR] amount 값이 유효하지 않습니다.");
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
        return amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
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