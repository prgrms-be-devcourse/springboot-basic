package org.prgrms.springorder.domain.wallet;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.voucher.Voucher;

public class Wallet {

	private final UUID walletId;
	private final Voucher voucher;
	private final Customer customer;
	private final LocalDateTime walletCreatedAt;

	public Wallet(UUID walletId, Voucher voucher, Customer customer, LocalDateTime walletCreatedAt) {
		this.walletId = walletId;
		this.voucher = voucher;
		this.customer = customer;
		this.walletCreatedAt = walletCreatedAt;
	}

	public UUID getWalletId() {
		return walletId;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public Customer getCustomer() {
		return customer;
	}

	public LocalDateTime getCreatedAt() {
		return walletCreatedAt;
	}
}
