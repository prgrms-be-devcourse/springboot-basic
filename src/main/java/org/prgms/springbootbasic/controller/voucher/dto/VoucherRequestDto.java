package org.prgms.springbootbasic.controller.voucher.dto;

public record VoucherRequestDto (String voucherId, String voucherPolicy, long discountDegree) {
}
