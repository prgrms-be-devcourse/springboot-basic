package com.prgrms.springbootbasic.service.customer;

import com.prgrms.springbootbasic.domain.customer.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    void createCustomers(List<Customer> customers);

    Customer createCustomer(String email, String name);

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomer(UUID customerId);
}
