package org.prgms.springbootbasic.domain.voucher;

public record VoucherRequestDto (String voucherId, String voucherPolicy, long discountDegree) {
}
