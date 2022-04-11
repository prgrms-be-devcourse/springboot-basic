package com.prgrms.kdt.springbootbasic.entity;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getDiscountedMoney(long beforeDiscount);
    long getDiscountAmount();
}
