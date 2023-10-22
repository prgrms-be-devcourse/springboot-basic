package com.weeklyMission.voucher.dto;

import com.weeklyMission.voucher.domain.Voucher;
import java.util.UUID;

public record VoucherResponse(
    UUID voucherId,
    long amount) {
    public VoucherResponse (Voucher voucher){
        this(voucher.getVoucherId(), voucher.getAmount());
    }

    @Override
    public String toString() {
        return voucherId + " voucher";
    }
}
