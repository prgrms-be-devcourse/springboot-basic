package com.prgrms.springbootbasic.voucher.domain;

import java.util.UUID;

public interface Voucher {

    void validate(int discountAmount);

    UUID getUUID();

    int getDiscountRate();
}
