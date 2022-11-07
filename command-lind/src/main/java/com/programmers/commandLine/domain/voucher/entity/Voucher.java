package com.programmers.commandLine.domain.voucher.entity;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    Integer discount(Integer beforeDiscount);
    String getType();

    String getDiscount();
}
