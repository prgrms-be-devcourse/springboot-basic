package org.prgrms.kdt.voucher.controller.dto;

import org.prgrms.kdt.voucher.domain.VoucherType;

import javax.validation.constraints.NotNull;

public record CreateVoucherApiRequest(@NotNull VoucherType voucherType,
                                      @NotNull double discountAmount) {
}