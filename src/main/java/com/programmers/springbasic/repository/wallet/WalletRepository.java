package com.programmers.springbasic.repository.wallet;

import java.util.List;
import java.util.UUID;

import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.entity.voucher.Voucher;

public interface WalletRepository {

	void insertVoucherForCustomer(UUID customerId, UUID voucherId);

	void deleteVoucherFromCustomer(UUID customerId, UUID voucherId);

	List<UUID> findVoucherIdsByCustomerId(UUID customerId);

	List<UUID> findCustomerIdsByVoucherId(UUID voucherId);
}
