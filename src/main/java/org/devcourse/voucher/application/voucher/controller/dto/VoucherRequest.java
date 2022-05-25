package org.devcourse.voucher.application.voucher.controller.dto;

import org.devcourse.voucher.application.voucher.model.VoucherType;

public record VoucherRequest(VoucherType voucherType, long discount) {
}
