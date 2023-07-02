package co.programmers.voucher_management.customer.repository;

import java.util.List;

import co.programmers.voucher_management.customer.entity.Customer;

public interface CustomerRepository {
	List<Customer> findByRating(Customer.Rating blacklist);
}
