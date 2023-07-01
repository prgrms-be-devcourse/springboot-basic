package com.programmers.vouchermanagement.voucher.dto;

import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import com.programmers.vouchermanagement.voucher.domain.Voucher;

import java.util.List;
import java.util.stream.Collectors;

public class VoucherDto {

    public record Request(DiscountType discountType, int discountAmount) {
    }

    public record Response(List<String> voucherName) {

        @Override
        public List<String> voucherName() {
            return voucherName;
        }

        public static VoucherDto.Response toDto(List<Voucher> vouchers) {
            List<String> vourcherList = vouchers.stream()
                    .map(voucher -> voucher.getClass().getSimpleName())
                    .collect(Collectors.toList());
            return new VoucherDto.Response(vourcherList);
        }
    }

}
