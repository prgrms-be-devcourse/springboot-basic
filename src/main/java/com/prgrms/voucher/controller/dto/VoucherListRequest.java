package com.prgrms.voucher.controller.dto;

import com.prgrms.voucher.model.VoucherType;

public record VoucherListRequest(VoucherType voucherType,
                                 String startCreatedAt) { }
