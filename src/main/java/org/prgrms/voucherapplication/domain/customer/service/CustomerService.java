package org.prgrms.voucherapplication.domain.customer.service;

import org.prgrms.voucherapplication.domain.customer.controller.dto.ResponseBlacklist;
import org.prgrms.voucherapplication.domain.customer.entity.Customer;

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
