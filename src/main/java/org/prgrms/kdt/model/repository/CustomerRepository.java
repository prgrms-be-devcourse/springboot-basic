package org.prgrms.kdt.model.repository;

import java.util.List;
import java.util.Optional;

import org.prgrms.kdt.model.entity.CustomerEntity;

public interface CustomerRepository {
	CustomerEntity saveCustomer(CustomerEntity customer);

	CustomerEntity updateCustomer(CustomerEntity updatedCustomer);

	List<CustomerEntity> findAllCustomers();

	Optional<CustomerEntity> findCustomerById(Long customerId);
}
