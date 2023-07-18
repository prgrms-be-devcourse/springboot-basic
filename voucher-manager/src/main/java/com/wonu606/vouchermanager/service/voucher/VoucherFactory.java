package com.wonu606.vouchermanager.service.voucher;

import com.wonu606.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.voucher.PercentageVoucher;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherDto;
import com.wonu606.vouchermanager.domain.voucher.discountvalue.FixedAmountValue;
import com.wonu606.vouchermanager.domain.voucher.discountvalue.PercentageDiscountValue;
import com.wonu606.vouchermanager.service.util.UUIDGenerator;
import org.springframework.stereotype.Component;

@Component
public class VoucherFactory {

    private final UUIDGenerator uuidGenerator;

    public VoucherFactory(UUIDGenerator uuidGenerator) {
        this.uuidGenerator = uuidGenerator;
    }

    public Voucher create(VoucherDto voucherDto) {
        VoucherType creationType = VoucherType.getVoucherTypeByName(voucherDto.getType());

        switch (creationType) {
            case FIXED:
                FixedAmountValue fixedAmountValue =
                        new FixedAmountValue(voucherDto.getDiscountValue());
                return new FixedAmountVoucher(uuidGenerator.generateUUID(), fixedAmountValue);
            case PERCENT:
                PercentageDiscountValue percentageDiscountValue =
                        new PercentageDiscountValue(voucherDto.getDiscountValue());
                return new PercentageVoucher(uuidGenerator.generateUUID(), percentageDiscountValue);
            default:
                throw new IllegalArgumentException("바우처 팩토리에서 생성할 수 없는 타입입니다.");
        }
    }
}
