package com.wonu606.vouchermanager.service.voucher.factory;

import com.wonu606.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.voucher.PercentageVoucher;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.service.voucher.param.VoucherCreateParam;
import com.wonu606.vouchermanager.domain.voucher.discountvalue.FixedAmountValue;
import com.wonu606.vouchermanager.domain.voucher.discountvalue.PercentageDiscountValue;
import com.wonu606.vouchermanager.service.util.UUIDGenerator;
import com.wonu606.vouchermanager.service.voucher.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class VoucherFactory {

    private final UUIDGenerator uuidGenerator;

    public VoucherFactory(UUIDGenerator uuidGenerator) {
        this.uuidGenerator = uuidGenerator;
    }

    public Voucher create(VoucherCreateParam voucherCreateParam) {
        VoucherType creationType = VoucherType.getVoucherTypeByName(voucherCreateParam.getType());

        switch (creationType) {
            case FIXED:
                FixedAmountValue fixedAmountValue =
                        new FixedAmountValue(voucherCreateParam.getDiscountValue());
                return new FixedAmountVoucher(uuidGenerator.generateUUID(), fixedAmountValue);
            case PERCENT:
                PercentageDiscountValue percentageDiscountValue =
                        new PercentageDiscountValue(voucherCreateParam.getDiscountValue());
                return new PercentageVoucher(uuidGenerator.generateUUID(), percentageDiscountValue);
            default:
                throw new IllegalArgumentException("바우처 팩토리에서 생성할 수 없는 타입입니다.");
        }
    }
}
