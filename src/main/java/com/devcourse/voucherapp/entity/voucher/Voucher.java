package com.devcourse.voucherapp.entity.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    String getTypeNumber();

    int getDiscountAmount();
}
