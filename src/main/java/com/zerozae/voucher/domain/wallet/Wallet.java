package com.zerozae.voucher.domain.wallet;

import lombok.Getter;

import java.util.UUID;

public record Wallet(UUID customerId, UUID voucherId) {

}
