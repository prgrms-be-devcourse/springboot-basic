package org.prgrms.assignment.voucher.entity;

import org.prgrms.assignment.voucher.model.VoucherType;

import java.time.LocalDateTime;

public record VoucherEntity(byte[] voucherId, VoucherType voucherType, LocalDateTime createdAt,
                            Long benefit, LocalDateTime expireDate) {
}
