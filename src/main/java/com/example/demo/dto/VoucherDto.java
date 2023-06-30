package com.example.demo.dto;

import com.example.demo.util.VoucherType;
import java.util.UUID;

public class VoucherDto {

    private final UUID id;
    private final int discountAmount;
    private final VoucherType voucherType;

    public VoucherDto(UUID id, int discountAmount, VoucherType voucherType) {
        this.id = id;
        this.discountAmount = discountAmount;
        this.voucherType = voucherType;
    }
}
