package org.prgrms.application.domain.voucher;

public record VoucherDto(Long voucherId, VoucherType voucherType, Double discountAmount) {

    public static VoucherDto of(Voucher voucher){
        return new VoucherDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountAmount());
    }
}
