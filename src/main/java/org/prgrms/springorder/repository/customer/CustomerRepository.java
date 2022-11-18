package org.prgrms.springorder.repository.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgrms.springorder.domain.customer.Customer;

public interface CustomerRepository {

	void save(Customer customer);

	void update(Customer customer);

	List<Customer> findAll();

	Optional<Customer> findById(UUID customerId);

}
