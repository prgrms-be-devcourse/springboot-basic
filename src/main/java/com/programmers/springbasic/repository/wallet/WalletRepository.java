package com.programmers.springbasic.repository.wallet;

import java.util.List;
import java.util.UUID;

import com.programmers.springbasic.entity.wallet.Wallet;

public interface WalletRepository {

	void insertVoucherForCustomer(UUID customerId, UUID voucherId);

	void deleteVoucherFromCustomer(UUID customerId, UUID voucherId);

	List<Wallet> findByCustomerId(UUID customerId);

	List<Wallet> findByVoucherId(UUID voucherId);
}
