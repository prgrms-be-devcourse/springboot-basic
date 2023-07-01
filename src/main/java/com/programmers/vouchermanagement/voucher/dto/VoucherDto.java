package com.programmers.vouchermanagement.voucher.dto;

import com.programmers.vouchermanagement.voucher.domain.DiscountType;

import java.util.List;

public class VoucherDto {

    public record Request(DiscountType discountType, int discountAmount) {

    }

    public record Response(List<String> voucherName) {

        @Override
        public List<String> voucherName() {
            return voucherName;
        }
    }

}
