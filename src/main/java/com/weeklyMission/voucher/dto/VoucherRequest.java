package com.weeklyMission.voucher.dto;

import com.weeklyMission.client.VoucherType;
import com.weeklyMission.voucher.domain.FixedAmountVoucher;
import com.weeklyMission.voucher.domain.PercentDiscountVoucher;
import com.weeklyMission.voucher.domain.Voucher;
import java.util.UUID;

public record VoucherRequest(
    String type,
    UUID id,
    int amount
) {
    public Voucher toEntity(){
        if(type.equals(VoucherType.Fixed.getType())){
            new FixedAmountVoucher(id, amount);
        }
        return new PercentDiscountVoucher(id, amount);
    }
}
