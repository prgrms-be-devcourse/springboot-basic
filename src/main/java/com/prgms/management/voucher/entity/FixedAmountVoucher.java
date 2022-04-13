package com.prgms.management.voucher.entity;

import com.prgms.management.voucher.exception.InvalidVoucherParameterException;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final static Integer MAX_AMOUNT = 10000;
    private final static Integer MIN_AMOUNT = 0;
    private final Integer amount;
    private UUID voucherId;

    public FixedAmountVoucher(Integer amount) {
        this(UUID.randomUUID(), amount);
    }

    public FixedAmountVoucher(UUID voucherId, Integer amount) {
        if (amount <= MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new InvalidVoucherParameterException(MIN_AMOUNT + "과 " + MAX_AMOUNT + "사이의 값을 입력해주세요.");
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
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
    public String toString() {
        return "Fixed Amount Voucher {" + "voucherId=" + voucherId + ", amount=" + amount + '}';
    }
}
