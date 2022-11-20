package com.programmers.VoucherManagementApplication.voucher;

import com.programmers.VoucherManagementApplication.io.Message;
import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;

import java.util.UUID;

public class PercentVoucher extends Voucher {

    public PercentVoucher(UUID voucherId, VoucherType voucherType, Amount amount) {
        super(voucherId, voucherType, amount);
        if (amount.getAmount() > 100) {
            throw new IllegalArgumentException(Message.INVALID_PERCENT.getMessage());
        }
    }

//    @Override
//    public long discount() {
//        return originPrice.getOriginPrice() - (originPrice.getOriginPrice() * amount.getAmount()) / 100;
//    }
}
