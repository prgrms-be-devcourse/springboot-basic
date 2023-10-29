package com.prgrms.springbasic.domain.voucher.dto;

public record CreateVoucherRequest(String discountType, long discountValue) {
}
