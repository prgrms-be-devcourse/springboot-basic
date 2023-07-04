package com.prgms.VoucherApp.domain.voucher;

import java.math.BigDecimal;
import java.util.UUID;

public interface Voucher {
    BigDecimal discount(BigDecimal beforeAmount);

    UUID getVoucherId();

    BigDecimal getDiscountAmount();

    VoucherType getVoucherType();
}
