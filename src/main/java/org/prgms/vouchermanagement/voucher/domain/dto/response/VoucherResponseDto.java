package org.prgms.vouchermanagement.voucher.domain.dto.response;

import org.prgms.vouchermanagement.voucher.VoucherType;

import java.util.UUID;

public record VoucherResponseDto(UUID voucherId, long discount, VoucherType voucherType) {
}
