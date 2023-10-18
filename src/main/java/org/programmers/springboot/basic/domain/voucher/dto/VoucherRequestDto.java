package org.programmers.springboot.basic.domain.voucher.dto;

import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;

public record VoucherRequestDto(VoucherType voucherType, Long discount) {
}
