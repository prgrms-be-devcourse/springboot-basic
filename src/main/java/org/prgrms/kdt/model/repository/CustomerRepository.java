package org.prgrms.kdt.model.repository;

import java.util.List;
import java.util.Optional;

import org.prgrms.kdt.model.entity.CustomerEntity;

public interface CustomerRepository {
	CustomerEntity create(CustomerEntity customer);

	CustomerEntity update(CustomerEntity updatedCustomer);

	List<CustomerEntity> findAll();

	CustomerEntity findById(Long customerId);
}
