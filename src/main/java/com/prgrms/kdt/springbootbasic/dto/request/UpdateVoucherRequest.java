package com.prgrms.kdt.springbootbasic.dto.request;

import java.util.UUID;

public record UpdateVoucherRequest(long amount, String voucherType) {
}
