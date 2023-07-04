package com.prgms.VoucherApp.domain.voucher;

import java.math.BigDecimal;

public interface VoucherPolicy {

    BigDecimal discount(BigDecimal beforeAmount);

    BigDecimal getDiscountAmount();
}
