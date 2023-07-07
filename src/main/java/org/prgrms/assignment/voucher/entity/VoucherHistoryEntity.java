package org.prgrms.assignment.voucher.entity;

import org.prgrms.assignment.voucher.model.VoucherStatus;

import java.time.LocalDateTime;

public record VoucherHistoryEntity(byte[] voucherId, VoucherStatus voucherStatus, LocalDateTime recordedTime) {
}
