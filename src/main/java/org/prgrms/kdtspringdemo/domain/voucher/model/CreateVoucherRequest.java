package org.prgrms.kdtspringdemo.domain.voucher.model;

public record CreateVoucherRequest(String voucherType, long amountOrPercent) {
}
