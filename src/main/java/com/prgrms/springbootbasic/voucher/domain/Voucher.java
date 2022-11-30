package com.prgrms.springbootbasic.voucher.domain;

import com.prgrms.springbootbasic.voucher.VoucherType;

import java.math.BigDecimal;
import java.util.UUID;

public interface Voucher {

    void validate(int discountAmount);

    UUID getUUID();

    int getDiscountAmount();

    VoucherType getVoucherType();

    BigDecimal discount(int beforeDiscount);

    void update(int discountAmount);
}
