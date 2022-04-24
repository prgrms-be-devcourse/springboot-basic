package com.prgms.management.voucher.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final static Integer MIN_PERCENT = 0;
    private final static Integer MAX_PERCENT = 100;
    
    public PercentDiscountVoucher(Integer percent) {
        this(UUID.randomUUID(), percent);
    }
    
    public PercentDiscountVoucher(String name, Integer percent) {
        this(UUID.randomUUID(), name, percent, Timestamp.valueOf(LocalDateTime.now()));
    }
    
    public PercentDiscountVoucher(UUID id, Integer percent) {
        this(id, "none", percent, Timestamp.valueOf(LocalDateTime.now()));
    }
    
    public PercentDiscountVoucher(UUID id, String name, Integer percent, Timestamp createdAt) {
        super(id, name, percent, createdAt, VoucherType.PERCENT, MAX_PERCENT, MIN_PERCENT);
    }
    
    @Override
    public Integer discount(Integer beforeDiscount) {
        return beforeDiscount * (100 - getFigure()) / 100;
    }
    
    @Override
    public String getStringForCSV() {
        return this.getClass().getCanonicalName() + "," + getId() + "," + getFigure();
    }
}
