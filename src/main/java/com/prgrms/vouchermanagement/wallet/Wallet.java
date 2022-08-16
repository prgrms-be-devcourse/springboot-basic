package com.prgrms.vouchermanagement.wallet;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.util.Assert;

public class Wallet {
	private final UUID customerId;
	private final UUID voucherId;
	private final LocalDateTime createdAt;

	public Wallet(UUID customerId, UUID voucherId) {
		this(customerId, voucherId, LocalDateTime.now());
	}

	public Wallet(UUID customerId, UUID voucherId, LocalDateTime createdAt) {
		Assert.notNull(customerId, "customer_id 는 null 일 수 없습니다");
		Assert.notNull(voucherId, "voucher_id 는 null 일 수 없습니다");
		Assert.notNull(createdAt, "createdAt 는 null 일 수 없습니다");

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
}
