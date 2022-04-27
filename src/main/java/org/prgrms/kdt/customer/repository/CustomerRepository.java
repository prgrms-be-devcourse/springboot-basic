package org.prgrms.kdt.customer.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.customer.model.Customer;

public interface CustomerRepository {

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    Customer save(Customer customer);

    void deleteAll();

}
