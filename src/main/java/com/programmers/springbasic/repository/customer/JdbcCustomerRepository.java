package com.programmers.springbasic.repository.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.customer.Customer;

@Repository
@Profile("test")
public class JdbcCustomerRepository implements CustomerRepository {

	@Override
	public Customer save(Customer customer) {
		return null;
	}

	@Override
	public List<Customer> findAll() {
		return null;
	}

	@Override
	public Optional<Customer> findById(Long id) {
		return Optional.empty();
	}

	@Override
	public List<Customer> findAllByIsBlackListedTrue() {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}
}
