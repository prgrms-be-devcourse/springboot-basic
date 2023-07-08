package com.ray.junho.voucher.controller.dto;

import com.ray.junho.voucher.domain.VoucherType;

public record VoucherRequest(VoucherType voucherType, int discountValue, int amount) {

}
