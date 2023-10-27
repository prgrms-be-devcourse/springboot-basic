package com.weeklyMission.voucher.dto;

import com.weeklyMission.voucher.domain.FixedAmountVoucher;
import com.weeklyMission.voucher.domain.Voucher;
import java.util.UUID;

public record VoucherResponse(
    UUID voucherId,
    String type,
    long amount) {
    public static VoucherResponse of(Voucher voucher){
        String type;
        if (voucher instanceof FixedAmountVoucher){
            type = "fixed";
        }
        else {
            type = "percent";
        }
        return new VoucherResponse(voucher.getVoucherId(), type, voucher.getAmount());
    }

    @Override
    public String toString() {
        return "VoucherId : " + voucherId + " Type : " + type + " Amount : " + amount;
    }
}
