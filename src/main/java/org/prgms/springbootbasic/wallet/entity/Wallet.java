package org.prgms.springbootbasic.wallet.entity;

import static com.google.common.base.Preconditions.*;

import org.prgms.springbootbasic.customer.entity.Customer;
import org.prgms.springbootbasic.voucher.entity.Voucher;

public class Wallet {
	private final Customer customer;
	private final Voucher voucher;
	private VoucherStatus status;

	public Wallet(Customer customer, Voucher voucher, VoucherStatus status) {
		checkArgument(customer != null, "customer은 null이면 안됩니다!");
		checkArgument(voucher != null, "voucher은 null이면 안됩니다!");
		checkArgument(status != null, "status는 null이면 안됩니다!");

		this.customer = customer;
		this.voucher = voucher;
		this.status = status;
	}

	public Wallet(Customer customer, Voucher voucher) {
		this(customer, voucher, VoucherStatus.AVAILABLE);
	}

}
