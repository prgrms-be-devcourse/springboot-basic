package org.prgrms.assignment.voucher.entity;

import org.prgrms.assignment.voucher.model.VoucherStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherHistoryEntity(UUID voucherId, UUID historyId, VoucherStatus voucherStatus, LocalDateTime recordedTime) {
}
