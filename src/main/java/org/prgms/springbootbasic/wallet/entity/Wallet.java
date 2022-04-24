package org.prgms.springbootbasic.wallet.entity;

import org.prgms.springbootbasic.customer.entity.Customer;
import org.prgms.springbootbasic.voucher.entity.Voucher;

public class Wallet {
	private final Customer customer;
	private final Voucher voucher;
	private VoucherStatus status;

	public Wallet(Customer customer, Voucher voucher, VoucherStatus status) {
		this.customer = customer;
		this.voucher = voucher;
		this.status = status;
	}

	public Wallet(Customer customer, Voucher voucher) {
		this(customer, voucher, VoucherStatus.AVAILABLE);
	}

}
