package org.programmers.springboot.basic.domain.voucher.dto;

import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;

import java.util.UUID;

public record CsvVoucherDto(UUID voucherId, VoucherType voucherType, Long discount) {
}
