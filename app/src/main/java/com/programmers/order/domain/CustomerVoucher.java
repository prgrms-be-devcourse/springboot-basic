package com.programmers.order.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerVoucher {
	private final UUID id;
	private final UUID customerId;
	private final UUID voucherId;
	private final LocalDateTime createdAt;

	public CustomerVoucher(UUID id, UUID customerId, UUID voucherId, LocalDateTime createdAt) {
		this.id = id;
		this.customerId = customerId;
		this.voucherId = voucherId;
		this.createdAt = createdAt;
	}

	public UUID getId() {
		return id;
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
}
