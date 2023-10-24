package study.dev.spring.customer.domain;

import java.util.List;

public interface CustomerRepository {

	List<Customer> findAll();
}
