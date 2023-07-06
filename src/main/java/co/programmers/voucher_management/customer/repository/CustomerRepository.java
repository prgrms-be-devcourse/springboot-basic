package co.programmers.voucher_management.customer.repository;

import java.util.List;
import java.util.Optional;

import co.programmers.voucher_management.customer.entity.Customer;

public interface CustomerRepository {

	List<Customer> findByRating(String rating);

	Optional<Customer> findById(long customerId);
}
