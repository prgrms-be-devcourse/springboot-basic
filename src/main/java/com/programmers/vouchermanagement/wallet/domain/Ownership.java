package com.programmers.vouchermanagement.wallet.domain;

import java.util.UUID;

public record Ownership(UUID voucherId, UUID customerId) {
}
