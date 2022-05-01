package org.prgms.management.repository.customer;

import org.prgms.management.model.customer.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface CustomerRepository {
    Customer insert(Customer customer);

    List<Customer> findAll();

    Customer findById(UUID customerId);

    Customer findByName(String name);

    Customer update(Customer customer);

    Customer delete(Customer customer);

    void deleteAll();
}
