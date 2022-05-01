package org.prgrms.voucherapplication.dto;

public record CreateVoucherRequest(String voucherType, long discountAmount) {
}
