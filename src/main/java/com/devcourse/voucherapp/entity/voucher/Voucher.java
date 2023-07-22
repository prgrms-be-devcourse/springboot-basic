package com.devcourse.voucherapp.entity.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getId();

    VoucherType getType();

    int getDiscountAmount();
}
