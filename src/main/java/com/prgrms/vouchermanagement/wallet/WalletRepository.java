package com.prgrms.vouchermanagement.wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.prgrms.vouchermanagement.customer.domain.Customer;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;

public interface WalletRepository {
	Optional<Voucher> findVoucherOfCustomer(UUID customerId, UUID voucherId);
	List<Voucher> findAllVouchersOfCustomer(UUID customerId);
	void disableVoucherOfCustomer(UUID customerId, UUID voucherId);
	void publishVoucherToCustomer(UUID customerId, UUID voucherId);
	long deleteRecord(UUID customerId, UUID voucherId);
	int insertRecord(UUID customerId, UUID voucherId);
	List<Customer> findAllCustomersOfVoucher(UUID voucherId);
}
