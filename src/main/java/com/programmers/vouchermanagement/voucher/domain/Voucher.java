package com.programmers.vouchermanagement.voucher.domain;

import java.util.UUID;

import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDTO;

public interface Voucher {
    UUID getVoucherId();

    long discount(long priceBeforeDiscount);

    VoucherResponseDTO toResponseDTO();
}
