package com.kdt.commandLineApp.voucher;

public class VoucherMapper {
    public static VoucherDTO toVoucherDTO(Voucher voucher) {
        return new VoucherDTO(voucher.getId().toString(), voucher.getType(), voucher.getDiscountAmount());
    }
}
