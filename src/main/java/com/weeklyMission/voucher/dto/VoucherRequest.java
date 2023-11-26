package com.weeklyMission.voucher.dto;

import com.weeklyMission.client.VoucherType;
import com.weeklyMission.voucher.domain.FixedAmountVoucher;
import com.weeklyMission.voucher.domain.PercentDiscountVoucher;
import com.weeklyMission.voucher.domain.Voucher;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherRequest{

    private String type;

    @NotNull
    private Integer amount;

    public Voucher toEntity(){
        VoucherType voucherType = VoucherType.of(type);
        String type = voucherType.getType();

        if(VoucherType.Fixed.getType().equals(type)){
            return new FixedAmountVoucher(UUID.randomUUID().toString(), amount);
        }
        else if(VoucherType.Percent.getType().equals(type)){
            return new PercentDiscountVoucher(UUID.randomUUID().toString(), amount);
        }
        return null;
    }
}
