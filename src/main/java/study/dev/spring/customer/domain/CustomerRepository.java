package study.dev.spring.customer.domain;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

	Customer save(Customer customer);

	Optional<Customer> findById(String uuid);
	List<Customer> findAll();
}
