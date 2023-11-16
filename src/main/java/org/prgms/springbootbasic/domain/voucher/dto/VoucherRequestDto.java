package org.prgms.springbootbasic.domain.voucher.dto;

public record VoucherRequestDto (String voucherId, String voucherPolicy, long discountDegree) {
}
