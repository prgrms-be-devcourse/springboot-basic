package com.programmers.voucher.domain.wallet.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Wallet {

	private final UUID customerId;
	private final UUID voucherId;
	private final LocalDateTime createdAt;

	public Wallet(UUID customerId, UUID voucherId, LocalDateTime createdAt) {
		this.customerId = customerId;
		this.voucherId = voucherId;
		this.createdAt = createdAt;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return "customerId" + customerId.toString() +
			", voucherId: " + voucherId.toString() +
			", createdAt: " + createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
