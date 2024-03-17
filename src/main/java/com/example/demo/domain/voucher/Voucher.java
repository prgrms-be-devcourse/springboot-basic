package com.example.demo.domain.voucher;

import com.example.demo.enums.VoucherDiscountType;
import java.util.UUID;

public interface Voucher {

    int discount(int beforeAmount);

    UUID getId();

    int getDiscountAmount();

    void updateDiscountAmount(int discountAmount);

    VoucherDiscountType getVoucherType();
}
