package org.programmers.springbootbasic.voucher.controller;

import java.util.UUID;

public record UpdateVoucherRequest(UUID voucherId, Long value) {
}
