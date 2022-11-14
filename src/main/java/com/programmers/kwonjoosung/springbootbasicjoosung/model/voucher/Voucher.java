package com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long getDiscount();

    VoucherType getVoucherType();

}
