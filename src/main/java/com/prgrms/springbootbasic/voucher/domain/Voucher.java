package com.prgrms.springbootbasic.voucher.domain;

import java.util.UUID;

public interface Voucher {

    void validate(String voucherType, int discountAmount);

    String getVoucherType();

    UUID getUUID();

    int getDiscountRate();
}
