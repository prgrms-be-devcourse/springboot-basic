package org.prgrms.kdtspringvoucher.dto;

import java.util.UUID;

public record VoucherDto(UUID voucherId, String voucherType, long amount) {
}
