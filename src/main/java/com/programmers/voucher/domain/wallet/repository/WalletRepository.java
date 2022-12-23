package com.programmers.voucher.domain.wallet.repository;

import java.util.List;
import java.util.UUID;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.wallet.model.Wallet;

public interface WalletRepository {

	Wallet save(Wallet wallet);

	List<Voucher> findVouchersByCustomerId(UUID customerId);

	List<Customer> findCustomersByVoucherId(UUID voucherId);

	void deleteByCustomerId(UUID customerId);

	void deleteAll();
}
