package com.prgms.VoucherApp.domain.voucher;

import com.prgms.VoucherApp.domain.voucher.dto.VoucherDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface Voucher {
    BigDecimal discount(BigDecimal beforeAmount);

    UUID getUUID();

    VoucherDto convertVoucherDto();
}
