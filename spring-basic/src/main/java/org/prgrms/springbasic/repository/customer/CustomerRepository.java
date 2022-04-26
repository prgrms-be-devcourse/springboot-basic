package org.prgrms.springbasic.repository.customer;

import org.prgrms.springbasic.domain.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findByCustomerId(UUID customerId);

    Optional<Customer> findByVoucherId(UUID voucherId);

    List<Customer> findCustomers();

    int countCustomers();

    Customer update(Customer customer);

    void deleteByCustomerId(UUID customerId);

    void deleteCustomers();
}
