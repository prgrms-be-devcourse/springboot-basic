package com.programmers.springbasic.repository.wallet;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {

	void insertVoucherForCustomer(UUID customerId, UUID voucherId);

	void deleteVoucherFromCustomer(UUID customerId, UUID voucherId);

	List<UUID> findVoucherIdsByCustomerId(UUID customerId);

	List<UUID> findCustomerIdsByVoucherId(UUID voucherId);
}
