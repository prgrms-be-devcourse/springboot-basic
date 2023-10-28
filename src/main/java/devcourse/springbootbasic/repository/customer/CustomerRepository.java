package devcourse.springbootbasic.repository.customer;

import devcourse.springbootbasic.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    List<Customer> findAllBlacklistedCustomers();

    Customer save(Customer customer);

    Optional<Customer> findById(UUID customerId);

    boolean update(Customer customer);
}
