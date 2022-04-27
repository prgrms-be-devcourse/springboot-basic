package com.kdt.commandLineApp.service.voucher;

public class VoucherConverter {
    public static VoucherDTO toVoucherDTO(Voucher voucher) {
        return new VoucherDTO(voucher.getId().toString(), voucher.getType(), voucher.getDiscountAmount());
    }
}
