package org.prgrms.kdt.voucher.controller.dto;

import org.prgrms.kdt.voucher.domain.VoucherType;

public record CreateVoucherControllerRequest(VoucherType voucherType, double discountAmount) {
}