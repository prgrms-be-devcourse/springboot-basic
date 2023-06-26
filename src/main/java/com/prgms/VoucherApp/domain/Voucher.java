package com.prgms.VoucherApp.domain;

import com.prgms.VoucherApp.dto.VoucherDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface Voucher {
    BigDecimal discount(BigDecimal beforeAmount);

    UUID getUUID();

    VoucherDto convertVoucherDto();
}
