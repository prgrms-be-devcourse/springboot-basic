package com.devcourse.voucher.domain;

import java.math.BigDecimal;

public interface DiscountPolicy {
    BigDecimal discount(long price);

    VoucherType getType();
}
