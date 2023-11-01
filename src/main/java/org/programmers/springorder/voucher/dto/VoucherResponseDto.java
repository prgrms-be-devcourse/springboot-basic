package org.programmers.springorder.voucher.dto;

import org.programmers.springorder.voucher.model.Voucher;

import java.util.UUID;


public record VoucherResponseDto( UUID voucherId, long discountValue, String voucherType){



    public static VoucherResponseDto of(Voucher voucher){
        return new VoucherResponseDto(
                voucher.getVoucherId(),
                voucher.getDiscountValue(),
                voucher.getVoucherType().name());
    }

    @Override
    public String toString() {
        return "ID : " + voucherId + '\n' +
                "Type : " + voucherType + '\n' +
                "Value : " + discountValue +'\n'+
                "==============================";
    }
}
