package com.devcourse.voucher;

import java.math.BigDecimal;

public interface DiscountPolicy {
    BigDecimal discount(long price);
}
