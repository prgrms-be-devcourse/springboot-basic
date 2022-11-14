package com.prgrms.springbootbasic.voucher.domain;

import java.math.BigDecimal;
import java.util.UUID;

public interface Voucher {

    void validate(int discountAmount);

    UUID getUUID();

    int getDiscountRate();

    BigDecimal discount(int beforeDiscount);
}
