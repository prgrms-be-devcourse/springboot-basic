package com.programmers.vouchermanagement.voucher.dto;

import java.util.List;
import java.util.stream.Collectors;

public class VoucherDto {

    public record Request(String discountType, int discountAmount) {

    }

    public record Response(List<String> voucherName) {

        @Override
        public String toString() {
            return voucherName.stream()
                    .map(voucher -> voucher + System.lineSeparator())
                    .collect(Collectors.joining());
        }
    }

}
