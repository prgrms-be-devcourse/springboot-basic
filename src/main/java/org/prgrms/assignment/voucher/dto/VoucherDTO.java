package org.prgrms.assignment.voucher.dto;

import org.prgrms.assignment.voucher.model.VoucherType;

import java.time.LocalDateTime;

public record VoucherDTO(VoucherType voucherType, long benefit, LocalDateTime createdAt,
                         LocalDateTime expireDate) {
}
