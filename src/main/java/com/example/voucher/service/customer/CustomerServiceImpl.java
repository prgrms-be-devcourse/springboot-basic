package com.example.voucher.service.customer;

import com.example.voucher.domain.customer.Customer;
import com.example.voucher.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerJdbcRepository;

	public CustomerServiceImpl(CustomerRepository customerJdbcRepository) {
		this.customerJdbcRepository = customerJdbcRepository;
	}

	@Override
	public List<Customer> findAll() {
		return customerJdbcRepository.findAll();
	}

	@Override
	public Customer save(String name) {
		if (name == null || name.isBlank())
			throw new IllegalArgumentException(INVALID_INPUT.getMessage());
		return customerJdbcRepository.save(new Customer(name));
	}

	@Override
	public void deleteById(Long customerId) {
		if (customerId == null) {
			throw new IllegalArgumentException(INVALID_INPUT.getMessage());
		}
		customerJdbcRepository.deleteById(customerId);
	}
}
