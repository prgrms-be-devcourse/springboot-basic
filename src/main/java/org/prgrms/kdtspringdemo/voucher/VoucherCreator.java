package org.prgrms.kdtspringdemo.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherCreator {
    public Voucher createVoucher(VoucherType voucherType,Long percentOrAmount) throws Exception{
        Voucher newVoucher =  switch (voucherType){
            case FIXED -> new FixedAmountVoucher(UUID.randomUUID(),percentOrAmount);
            case PERCENT -> new PercentDiscountVoucher(UUID.randomUUID(),percentOrAmount);
            case ERROR -> throw new Exception("알 수 없는 바우처 타입 입니다.");
        };
        return newVoucher;
    }
}
