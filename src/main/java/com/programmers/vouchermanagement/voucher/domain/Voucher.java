package com.programmers.vouchermanagement.voucher.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    VoucherType getVoucherType();

    Long getDiscount();
}
