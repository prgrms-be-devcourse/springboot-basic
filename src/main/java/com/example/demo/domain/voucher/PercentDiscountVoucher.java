package com.example.demo.domain.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID id;
    private final int discountPercent;

    public PercentDiscountVoucher(UUID id, int discountPercent) {
        this.id = id;
        this.discountPercent = discountPercent;
    }
}
