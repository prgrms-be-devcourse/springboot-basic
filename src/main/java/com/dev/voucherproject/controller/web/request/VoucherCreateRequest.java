package com.dev.voucherproject.controller.web.request;

import com.dev.voucherproject.model.voucher.VoucherPolicy;


public record VoucherCreateRequest(VoucherPolicy voucherPolicy, long discountFigure) {
}
