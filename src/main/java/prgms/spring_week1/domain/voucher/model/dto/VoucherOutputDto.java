package prgms.spring_week1.domain.voucher.model.dto;

import prgms.spring_week1.domain.voucher.model.type.VoucherType;

public record VoucherOutputDto(VoucherType voucherType,int discount) {
    @Override
    public String toString() {
        if (voucherType == VoucherType.FIXED) {
            return "상품권 종류 : 고정 가격 할인 상품권 " +
                    "할인 가격 :" + discount + "원";
        }
        return "상품권 종류 : 고정 가격 할인 상품권 " +
                "할인률 :" + discount + " 퍼센트";
    }
}
