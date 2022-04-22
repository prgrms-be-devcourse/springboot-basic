package com.pppp0722.vouchermanagement.voucher.model;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    VoucherType getType();

    long getAmount();

    UUID getMemberId();
}
