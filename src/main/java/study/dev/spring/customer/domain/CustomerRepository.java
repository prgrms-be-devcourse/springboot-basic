package study.dev.spring.customer.domain;

import java.util.List;

public interface CustomerRepository {

	Customer save(Customer customer);
	List<Customer> findAll();
}
