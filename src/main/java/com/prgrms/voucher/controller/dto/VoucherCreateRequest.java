package com.prgrms.voucher.controller.dto;

import com.prgrms.voucher.model.VoucherType;

public record VoucherCreateRequest(VoucherType voucherType,
                                   double discountAmount) { }
