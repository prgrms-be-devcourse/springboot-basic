package com.example.demo.voucher.domain;

import com.example.demo.voucher.application.VoucherType;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
    String getName();
    long getValue();
    VoucherType getType();
}
