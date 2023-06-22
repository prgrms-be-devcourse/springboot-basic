package com.programmers.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    String getVoucherName();

    long getDiscountValue();
}
