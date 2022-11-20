package com.programmers.voucher.domain.wallet.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.voucher.model.Voucher;

public class Wallet {

	private final Voucher voucher;
	private final Customer customer;
	private final LocalDateTime createdAt;

	public Wallet(Voucher voucher, Customer customer, LocalDateTime createdAt) {
		this.voucher = voucher;
		this.customer = customer;
		this.createdAt = createdAt;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public Customer getCustomer() {
		return customer;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return "voucherId: " + voucher.getVoucherId().toString() +
			", customerId" + customer.getCustomerId().toString() +
			", createdAt: " + createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
