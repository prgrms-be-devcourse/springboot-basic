package org.prgrms.kdt.dto;

import java.util.UUID;

public record VoucherDto(UUID voucherId, UUID customerId, Long amount) {

}