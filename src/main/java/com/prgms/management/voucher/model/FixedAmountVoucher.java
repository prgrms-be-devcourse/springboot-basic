package com.prgms.management.voucher.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final static Integer MIN_AMOUNT = 0;
    private final static Integer MAX_AMOUNT = 10000;

    public FixedAmountVoucher(Integer amount) {
        this(UUID.randomUUID(), amount);
    }

    public FixedAmountVoucher(String name, Integer amount) {
        this(UUID.randomUUID(), name, amount, Timestamp.valueOf(LocalDateTime.now()));
    }

    public FixedAmountVoucher(UUID id, Integer amount) {
        this(id, "none", amount, Timestamp.valueOf(LocalDateTime.now()));
    }

    public FixedAmountVoucher(UUID id, String name, Integer amount, Timestamp createdAt) {
        super(id, name, amount, createdAt, VoucherType.FIXED, MAX_AMOUNT, MIN_AMOUNT);
    }

    @Override
    public Integer discount(Integer beforeDiscount) {
        return beforeDiscount - getFigure();
    }

    @Override
    public String getStringForCSV() {
        return this.getClass().getCanonicalName() + "," + getId() + "," + getFigure();
    }
}
