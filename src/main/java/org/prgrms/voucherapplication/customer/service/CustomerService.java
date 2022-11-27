package org.prgrms.voucherapplication.customer.service;

import org.prgrms.voucherapplication.customer.controller.dto.ResponseBlacklist;
import org.prgrms.voucherapplication.customer.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    void createCustomers(List<Customer> customers);

    List<ResponseBlacklist> findBlacklist();

    List<Customer> getAllCustomers();

    Customer createCustomer(String email, String name);

    Optional<Customer> getCustomer(UUID customerId);

    void deleteCustomer(UUID customerId);
}
