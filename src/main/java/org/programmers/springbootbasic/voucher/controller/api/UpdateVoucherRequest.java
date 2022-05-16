package org.programmers.springbootbasic.voucher.controller.api;

import java.util.UUID;

public record UpdateVoucherRequest(UUID voucherId, Long value) {
}
