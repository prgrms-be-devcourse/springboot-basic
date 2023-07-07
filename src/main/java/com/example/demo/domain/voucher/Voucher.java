package com.example.demo.domain.voucher;

import com.example.demo.util.VoucherType;
import java.util.UUID;

public interface Voucher {

    double discount(double beforeAmount);

    UUID getId();

    double getDiscountAmount();

    VoucherType getVoucherType();
}
