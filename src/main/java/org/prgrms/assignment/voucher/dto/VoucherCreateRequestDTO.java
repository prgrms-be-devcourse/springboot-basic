package org.prgrms.assignment.voucher.dto;

import org.prgrms.assignment.voucher.model.VoucherType;

public record VoucherCreateRequestDTO(
    VoucherType voucherType,
    long benefit,
    long durationDate){
}