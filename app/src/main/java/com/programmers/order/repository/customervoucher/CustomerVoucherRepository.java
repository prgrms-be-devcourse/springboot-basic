package com.programmers.order.repository.customervoucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.programmers.order.domain.Customer;
import com.programmers.order.domain.CustomerVoucher;
import com.programmers.order.domain.Voucher;

public interface CustomerVoucherRepository {
	CustomerVoucher insert(CustomerVoucher customerVoucher);

	Optional<CustomerVoucher> findByVoucherId(UUID voucherId);
	Optional<Voucher> findVoucherByVoucherId(UUID voucherId);

	Optional<CustomerVoucher> findByCustomerId(UUID customerId);

	boolean isDuplicatePublish(UUID customerId, UUID voucherId);

	List<Voucher> findVouchersByCustomerId(UUID customerId);

	List<Customer> joinCustomers(UUID voucherId);

	void deleteByCustomerIdAndVoucherId(UUID customerIdentity, UUID voucherIdentity);
}
