package org.prgrms.assignment.voucher.entity;

import org.prgrms.assignment.voucher.model.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherEntity(UUID voucherId, VoucherType voucherType, LocalDateTime createdAt,
                            Long benefit, LocalDateTime expireDate) {
}
