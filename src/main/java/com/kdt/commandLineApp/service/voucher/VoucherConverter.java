package com.kdt.commandLineApp.service.voucher;

public class VoucherConverter {
    public static VoucherDTO toVoucherDTO(Voucher voucher) {
        return new VoucherDTO(Long.toString(voucher.getId()), voucher.getType(), voucher.getDiscountAmount());
    }
}
