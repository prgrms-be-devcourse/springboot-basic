package org.prgrms.assignment.customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    void createCustomers(List<Customer> customers);

    Customer createCustomer(UUID customerId, String name, String email);

    Customer updateCustomer(UUID customerId, String name, String email, LocalDateTime createdAt);

    Customer findById(UUID customerId);

    Customer findByEmail(String email);

    Customer findByName(String name);

    void deleteCustomer(UUID customerId);

    void deleteAll();
}
