package com.programmers.vouchermanagement.voucher.dto;

public class VoucherDto {

    public record RequestDto(String discountType, int discountAmount) {

    }
}
