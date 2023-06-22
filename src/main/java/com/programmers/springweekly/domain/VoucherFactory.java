package com.programmers.springweekly.domain;

import java.util.UUID;

public class VoucherFactory {

    public Voucher createVoucher(VoucherMenu voucherMenu, long discount){
        if(voucherMenu.equals(VoucherMenu.FIXED)){
            return new FixedAmountVoucher(UUID.randomUUID(), discount);
        }

        if(voucherMenu.equals(VoucherMenu.PERCENT)){
            return new PercentDiscountVoucher(UUID.randomUUID(), discount);
        }

        throw new IllegalArgumentException("없는 바우처 메뉴 입니다.");
    }
}
