package com.ray.junho.voucher.controller.dto;

import com.ray.junho.voucher.domain.VoucherType;

public record VoucherResponse(long id, VoucherType voucherType, int discountValue) {

    public String generateMessage() {
        return String.format("Voucher 번호: %d, 타입: %s, 할인 값: %d", id, voucherType, discountValue);
    }
}
