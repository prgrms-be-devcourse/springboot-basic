package com.programmers.vouchermanagement.voucher.dto;

public class VoucherDto {

    public record Request(String discountType, int discountAmount) {

    }
}
