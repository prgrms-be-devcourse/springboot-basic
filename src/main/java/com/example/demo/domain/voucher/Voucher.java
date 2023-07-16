package com.example.demo.domain.voucher;

import com.example.demo.util.VoucherDiscountType;
import java.util.UUID;

public interface Voucher {

    double discount(double beforeAmount);

    UUID getId();

    double getDiscountAmount();

    void updateDiscountAmount(double discountAmount);

    VoucherDiscountType getVoucherType();
}
