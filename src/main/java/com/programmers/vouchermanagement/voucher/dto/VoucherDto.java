package com.programmers.vouchermanagement.voucher.dto;

import java.util.List;

public class VoucherDto {

    public record Request(String discountType, int discountAmount) {

    }

    public record Response(List<String> voucherName) {
    }

}
