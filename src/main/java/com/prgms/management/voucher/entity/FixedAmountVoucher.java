package com.prgms.management.voucher.entity;

import com.prgms.management.voucher.exception.InvalidVoucherParameterException;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final static Long MAX_AMOUNT = 10000L;
    private final static Long MIN_AMOUNT = 0L;
    private final Long amount;
    private UUID voucherId;

    public FixedAmountVoucher(UUID voucherId, Long amount) {
        if (amount <= MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new InvalidVoucherParameterException(MIN_AMOUNT + "과 " + MAX_AMOUNT + "사이의 값을 입력해주세요.");
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public FixedAmountVoucher(Long amount) {
        this(UUID.randomUUID(), amount);
    }

    public static Long getMinAmount() {
        return MIN_AMOUNT;
    }

    public static Long getMaxAmount() {
        return MAX_AMOUNT;
    }

    @Override
    public void resetVoucherId() {
        voucherId = UUID.randomUUID();
    }

    @Override
    public Long discount(Long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String getStringForCSV() {
        return this.getClass().getCanonicalName() + "," + voucherId + "," + amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "Fixed Amount Voucher {" + "voucherId=" + voucherId + ", amount=" + amount + '}';
    }
}
