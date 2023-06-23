package prgms.spring_week1.domain.voucher.model.impl;

import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;

import java.util.UUID;


public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(UUID voucherId, VoucherType voucherType, long discount) {
        super(voucherId, voucherType, discount);
    }

    @Override
    public String toString() {
        return "상품권 종류 : 고정 가격 할인 상품권" +
                "할인  가격 :" + discount + "원";
    }

}
