package org.prgrms.kdtspringdemo.voucher.model.dto;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;

import java.util.UUID;

public record VoucherResponse(UUID id, VoucherType type, long amount) {
}
