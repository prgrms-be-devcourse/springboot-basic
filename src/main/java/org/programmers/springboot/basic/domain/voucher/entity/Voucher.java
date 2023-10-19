package org.programmers.springboot.basic.domain.voucher.entity;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();
    VoucherType getVoucherType();
    Long getDiscount();
}
