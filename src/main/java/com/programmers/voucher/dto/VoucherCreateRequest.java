package com.programmers.voucher.dto;

import com.programmers.voucher.domain.DiscountType;

public record VoucherCreateRequest(DiscountType discountType, long value){
}
