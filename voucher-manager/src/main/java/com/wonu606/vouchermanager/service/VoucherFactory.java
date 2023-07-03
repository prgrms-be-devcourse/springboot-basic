package com.wonu606.vouchermanager.service;

import com.wonu606.vouchermanager.domain.discountvalue.FixedAmountValue;
import com.wonu606.vouchermanager.domain.discountvalue.PercentageDiscountValue;
import com.wonu606.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.voucher.PercentageVoucher;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherDto;
import org.springframework.stereotype.Component;

@Component
public class VoucherFactory {

    public Voucher create(VoucherDto voucherDto) {
        VoucherType creationType = VoucherType.getVoucherTypeByName(voucherDto.getType());

        switch (creationType) {
            case FIXED:
                FixedAmountValue fixedAmountValue =
                        new FixedAmountValue(voucherDto.getDiscountValue());
                return new FixedAmountVoucher(voucherDto.getUuid(), fixedAmountValue);
            case PERCENT:
                PercentageDiscountValue percentageDiscountValue =
                        new PercentageDiscountValue(voucherDto.getDiscountValue());
                return new PercentageVoucher(voucherDto.getUuid(), percentageDiscountValue);
            default:
                throw new IllegalArgumentException("바우처 팩토리에서 생성할 수 없는 타입입니다.");
        }
    }
}
