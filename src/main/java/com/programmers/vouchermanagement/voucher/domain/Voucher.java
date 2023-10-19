package com.programmers.vouchermanagement.voucher.domain;

import com.programmers.vouchermanagement.voucher.dto.GeneralVoucherDTO;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long getDiscountValue();

    long discount(long priceBeforeDiscount);

    GeneralVoucherDTO toVoucherDTO();
}
