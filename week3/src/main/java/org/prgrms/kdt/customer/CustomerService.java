package org.prgrms.kdt.customer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    void createCustomers(List<Customer> customers);

    List<Customer> getAllCustomer();

    Optional<Customer> getCustomer(UUID customerId);

    Customer createCustomer(String email, String name);
}
