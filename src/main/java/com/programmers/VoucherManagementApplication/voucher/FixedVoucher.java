package com.programmers.VoucherManagementApplication.voucher;

import com.programmers.VoucherManagementApplication.io.Message;
import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;

import java.util.UUID;

public class FixedVoucher extends Voucher {

    private static final long MAX_VOUCHER_AMOUNT = 10000;

    public FixedVoucher(UUID voucherId, VoucherType voucherType, Amount amount) {
        super(voucherId, voucherType, amount);
        if (amount.getAmount() > MAX_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException(Message.INVALID_FIXED_MAX.getMessage());
        }
    }

//    @Override
//    public long discount() {
//        long discount = super.originPrice.getOriginPrice() - super.amount.getAmount();
//        return discount < 0 ? 0 : discount;
//    }
}
