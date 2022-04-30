package org.prgms.controller.dto;

public record CreateVoucherRequest(String voucherKind, long discountAmount) {
}
