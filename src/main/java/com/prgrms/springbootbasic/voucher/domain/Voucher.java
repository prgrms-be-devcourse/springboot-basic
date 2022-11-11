package com.prgrms.springbootbasic.voucher.domain;

import java.util.UUID;

public interface Voucher {

    String getVoucherType();

    UUID getUUID();

    int getDiscountRate();
}
