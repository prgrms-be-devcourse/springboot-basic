package org.programmers.springbootbasic.application.voucher.controller.api;

import java.util.UUID;

public record UpdateVoucherRequest(UUID voucherId, Long value) {
}
