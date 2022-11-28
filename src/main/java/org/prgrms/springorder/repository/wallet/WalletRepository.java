package org.prgrms.springorder.repository.wallet;

import java.util.List;
import java.util.UUID;

import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.wallet.Wallet;

public interface WalletRepository {

	void allocate(Wallet wallet);

	List<Voucher> findVoucherByCustomerId(UUID customerId);

	void delete(Wallet wallet);

	List<Customer> findCustomerByVoucherId(UUID voucherId);

	void clear();
}
