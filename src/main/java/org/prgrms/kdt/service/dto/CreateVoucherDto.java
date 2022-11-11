package org.prgrms.kdt.service.dto;

import org.prgrms.kdt.domain.VoucherType;

public record CreateVoucherDto(VoucherType voucherType, double discountAmount) {

}
