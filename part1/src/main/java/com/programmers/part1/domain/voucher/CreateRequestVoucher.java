package com.programmers.part1.domain.voucher;

import lombok.Getter;

@Getter
public class CreateRequestVoucher {
    private final String voucherType;
    private final int amount;

    public CreateRequestVoucher(String voucherType, int amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }
}
