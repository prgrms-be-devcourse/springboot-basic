package org.prgms.w3d1.model.voucher;

import java.util.UUID;

public class VoucherFactory {
    public Voucher createVoucher(VoucherType voucherType, long discountValue){
        if(voucherType == VoucherType.FIXED_AMOUNT_TYPE){
            return new FixedAmountVoucher(UUID.randomUUID(), discountValue);
        }else{
            return new PercentDiscountVoucher(UUID.randomUUID(), discountValue);
        }
    }
}
