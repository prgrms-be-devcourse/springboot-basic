package com.programmers.VoucherManagementApplication.voucher;

import com.programmers.VoucherManagementApplication.dto.Amount;
import com.programmers.VoucherManagementApplication.dto.VoucherType;

import java.util.UUID;

public class PercentVoucherAmount extends Voucher {

    public PercentVoucherAmount(UUID voucherId, VoucherType voucherType, Amount amount) {
        super(voucherId, voucherType, amount);
        if(super.amount.getAmount() > 100) throw new IllegalArgumentException("퍼센트 오류");
    }

//    @Override
//    public long discount() {
//        return originPrice.getOriginPrice() - (originPrice.getOriginPrice() * amount.getAmount()) / 100;
//    }
}
