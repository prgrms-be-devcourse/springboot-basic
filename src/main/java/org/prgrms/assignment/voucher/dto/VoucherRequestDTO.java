package org.prgrms.assignment.voucher.dto;

import org.prgrms.assignment.voucher.model.VoucherType;


public record VoucherRequestDTO (VoucherType voucherType, long benefit,
                                long durationDate){
}
