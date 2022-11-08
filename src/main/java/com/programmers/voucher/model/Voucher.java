package com.programmers.voucher.model;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount();
}
