package org.prgrms.voucherapplication.dto;

import java.util.UUID;

public record IssueVoucherRequest(UUID voucherId, UUID customerId) {
}
