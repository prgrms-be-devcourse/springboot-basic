package org.prgrms.kdt.model.repository;

import org.prgrms.kdt.model.entity.CustomerEntity;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
	CustomerEntity create(CustomerEntity customer);
	CustomerEntity update(CustomerEntity updatedCustomer);
	List<CustomerEntity> findAll();
	Optional<CustomerEntity> findById(Long customerId);
}
