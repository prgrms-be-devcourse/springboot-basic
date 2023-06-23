package prgms.spring_week1.domain.voucher.model.impl;

import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;

import java.util.UUID;


public class PercentDiscountVoucher extends Voucher {
    public PercentDiscountVoucher(UUID voucherId, VoucherType voucherType, long discount) {
        super(voucherId, voucherType, discount);
    }

    @Override
    public String toString() {
        return "상품권 종류 : 할인률 적용 상품권" +
                "할인률 :" + discount + "퍼센트";
    }


}
