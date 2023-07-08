package com.prgrms.model.voucher.dto;

import com.prgrms.model.voucher.dto.discount.Discount;
import com.prgrms.model.voucher.VoucherType;

public record VoucherRequest(VoucherType voucherType, Discount discount) {

}
