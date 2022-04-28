package org.prgms.management.model.voucher;

public record VoucherDto(
        String voucherName,
        String voucherType,
        int discountNum
) {
}
