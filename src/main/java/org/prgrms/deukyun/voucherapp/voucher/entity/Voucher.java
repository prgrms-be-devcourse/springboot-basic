package org.prgrms.deukyun.voucherapp.voucher.entity;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount(long beforeDiscount);
}
