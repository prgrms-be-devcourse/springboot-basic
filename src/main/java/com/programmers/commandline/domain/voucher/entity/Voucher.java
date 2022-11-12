package com.programmers.commandline.domain.voucher.entity;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    VoucherType getType();
    Long getDiscount();
}
