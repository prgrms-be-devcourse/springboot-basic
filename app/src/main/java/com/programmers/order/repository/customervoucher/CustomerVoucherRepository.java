package com.programmers.order.repository.customervoucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.programmers.order.domain.Customer;
import com.programmers.order.domain.Wallet;
import com.programmers.order.domain.Voucher;

public interface CustomerVoucherRepository {
	Wallet insert(Wallet wallet);

	Optional<Wallet> findByVoucherId(UUID voucherId);
	Optional<Voucher> findVoucherByVoucherId(UUID voucherId);

	Optional<Wallet> findByCustomerId(UUID customerId);

	boolean isDuplicatePublish(UUID customerId, UUID voucherId);

	List<Voucher> findVouchersByCustomerId(UUID customerId);

	List<Customer> joinCustomers(UUID voucherId);

	void deleteByCustomerIdAndVoucherId(UUID customerIdentity, UUID voucherIdentity);
}
