package study.dev.spring.customer.stub;

import java.util.List;
import java.util.UUID;

import study.dev.spring.customer.domain.Customer;
import study.dev.spring.customer.domain.CustomerRepository;

public class CustomerRepositoryStub implements CustomerRepository {

	@Override
	public List<Customer> findAll() {
		return List.of(new Customer(UUID.randomUUID().toString(), "customer"));
	}
}
