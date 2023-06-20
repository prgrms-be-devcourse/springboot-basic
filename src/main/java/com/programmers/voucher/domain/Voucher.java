package com.programmers.voucher.domain;

import java.util.UUID;

public interface Voucher {

    long totalAmount(long beforeAmount);

    UUID getVoucherId();
}
