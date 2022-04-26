package org.prgrms.spring_week1.customer.model.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.spring_week1.customer.model.Customer;

public interface CustomerRepository {
    List<Customer> getAll();

    Optional<Customer> findById(UUID customerId);

    Customer insert(Customer customer);

    Customer update(Customer customer);

    void deleteAll();
}
