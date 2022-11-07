package org.prgrms.kdtspringdemo.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherCreator {
    public Voucher createVoucher(VoucherType voucherType,Long percentOrAmount) throws Exception{
        Voucher newVoucher =  switch (voucherType){
            case FIXED -> new FixedAmountVoucher(UUID.randomUUID(),percentOrAmount);
            case PERCENT -> new PercentDiscountVoucher(UUID.randomUUID(),percentOrAmount);
        };
        return newVoucher;
    }
}
