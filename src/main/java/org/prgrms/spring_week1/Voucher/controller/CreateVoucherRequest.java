package org.prgrms.spring_week1.Voucher.controller;

import java.util.UUID;

public record CreateVoucherRequest(String voucherType, String discount, UUID customerId) {

}
