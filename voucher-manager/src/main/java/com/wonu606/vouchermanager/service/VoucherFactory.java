package com.wonu606.vouchermanager.service;

import com.wonu606.vouchermanager.domain.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.PercentageVoucher;
import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.domain.VoucherDto;
import java.util.UUID;

public class VoucherFactory {

    public Voucher create(VoucherDto voucherDto) {
        VoucherType creationType = VoucherType.getVoucherTypeByName(voucherDto.getType());
        
        switch (creationType) {
            case FIXED:
                return new FixedAmountVoucher(voucherDto.getUuid(), voucherDto.getDiscountValue());
            case PERCENT:
                return new PercentageVoucher(voucherDto.getUuid(), voucherDto.getDiscountValue());
            default:
                throw new IllegalArgumentException("바우처 팩토리에서 생성할 수 없는 타입입니다.");
        }
    }
}
