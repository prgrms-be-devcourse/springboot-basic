package org.prgms.order.customer.service;

import org.prgms.order.customer.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<Customer> findAllBlackList();

    void createCustomers(List<Customer> customers);

    Customer createCustomer(String email, String name);

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomer(UUID customerId);


}
