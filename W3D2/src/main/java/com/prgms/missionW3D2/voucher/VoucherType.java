package com.prgms.missionW3D2.voucher;

import java.util.UUID;

public enum VoucherType {
    FixedAmountVoucher(){
        Voucher createVoucher(VoucherRepository voucherRepository, UUID voucherId, long figure){
            Voucher newVoucher = new FixedAmountVoucher(voucherId, figure);
            voucherRepository.insert(newVoucher);
            return newVoucher;
        }
    },
    PercentDiscountVoucher(){
        Voucher createVoucher(VoucherRepository voucherRepository, UUID voucherId, long figure){
            Voucher newVoucher = new PercentDiscountVoucher(voucherId, figure);
            voucherRepository.insert(newVoucher);
            return newVoucher;
        }
    };

    abstract Voucher createVoucher(VoucherRepository voucherRepository, UUID voucherId, long figure);
}
