package com.programmers.vouchermanagement.dto.voucher.request;

import com.programmers.vouchermanagement.domain.voucher.VoucherType;

public record CreateVoucherRequestDto(VoucherType type, Long amount) {
}
