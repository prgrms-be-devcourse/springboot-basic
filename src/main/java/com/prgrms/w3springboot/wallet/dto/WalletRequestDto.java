package com.prgrms.w3springboot.wallet.dto;

import java.util.UUID;

import com.prgrms.w3springboot.wallet.Wallet;

public class WalletRequestDto {
	private final UUID walletId;
	private final UUID customerId;
	private final UUID voucherId;

	public WalletRequestDto(UUID walletId, UUID customerId, UUID voucherId) {
		this.walletId = walletId;
		this.customerId = customerId;
		this.voucherId = voucherId;
	}

	public UUID getWalletId() {
		return walletId;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	public Wallet toEntity() {
		return new Wallet(walletId, customerId, voucherId);
	}

}
