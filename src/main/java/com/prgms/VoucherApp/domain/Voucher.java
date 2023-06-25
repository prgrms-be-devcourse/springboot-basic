package com.prgms.VoucherApp.domain;

import com.prgms.VoucherApp.dto.VoucherDto;

import java.util.UUID;

public interface Voucher {
    long discount(long beforeAmount);

    UUID getUUID();

    VoucherDto convertVoucherDto();
}
