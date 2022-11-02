package com.programmers.commandlind.entity;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();
    Long discount(Long beforeDiscount);
}
