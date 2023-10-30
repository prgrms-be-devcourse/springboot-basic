package com.programmers.vouchermanagement.dto.wallet;

import java.util.UUID;


public record CreateWalletRequestDto(UUID customerId, UUID voucherId) {
}
