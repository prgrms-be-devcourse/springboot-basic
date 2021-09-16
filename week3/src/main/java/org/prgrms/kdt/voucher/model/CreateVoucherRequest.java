package org.prgrms.kdt.voucher.model;

public record CreateVoucherRequest(String walletId, String voucherType, Long amount, String customerId) {
}
