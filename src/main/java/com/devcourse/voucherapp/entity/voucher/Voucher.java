package com.devcourse.voucherapp.entity.voucher;

import com.devcourse.voucherapp.entity.VoucherType;
import java.util.UUID;

public interface Voucher {

    UUID getId();

    VoucherType getType();

    int getDiscountAmount();
}
