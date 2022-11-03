package com.programmers.commandLine.domain.voucher.entity;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    Long discount(Long beforeDiscount);
}
