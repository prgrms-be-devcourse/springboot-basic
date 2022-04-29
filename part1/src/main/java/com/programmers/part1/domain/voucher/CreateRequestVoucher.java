package com.programmers.part1.domain.voucher;

import lombok.Getter;

@Getter
public class CreateRequestVoucher {
    private String voucherType;
    private int amount;
}
