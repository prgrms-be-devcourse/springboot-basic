package com.prgrms.w3springboot.wallet.repository;

import java.util.List;
import java.util.UUID;

import com.prgrms.w3springboot.customer.Customer;
import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.wallet.Wallet;

public interface WalletRepository {
	Wallet insert(Wallet wallet);

	List<Voucher> findVouchersByCustomerId(UUID customerId);

	void delete(UUID walletId);

	List<Customer> findCustomersByVoucherId(UUID voucherId);
}
