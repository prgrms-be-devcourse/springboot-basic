package org.prgrms.kdt.voucher.service.dto;

import org.prgrms.kdt.voucher.domain.VoucherType;

public record CreateVoucherRequest(VoucherType voucherType, double discountAmount) {
}
