package com.programmers.VoucherManagementApplication.voucher;

import com.programmers.VoucherManagementApplication.dto.Amount;
import com.programmers.VoucherManagementApplication.dto.VoucherType;

import java.util.UUID;

public class FixedVoucherAmount extends Voucher {

    private static final long MAX_VOUCHER_AMOUNT = 10000;

    public FixedVoucherAmount(UUID voucherId, VoucherType voucherType, Amount amount) {
        super(voucherId, voucherType, amount);
        if(amount.getAmount() > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException("fix amount greater");
    }

//    @Override
//    public long discount() {
//        long discount = super.originPrice.getOriginPrice() - super.amount.getAmount();
//        return discount < 0 ? 0 : discount;
//    }
}
