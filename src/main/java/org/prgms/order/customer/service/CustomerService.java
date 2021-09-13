package org.prgms.order.customer.service;

import org.prgms.order.customer.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<Customer> findAllBlackList();
    void insertBlackListById(UUID customerId);

    void createCustomers(List<Customer> customers);
    Customer createCustomer(String email, String name);

    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAllCustomer();

    Customer modifyCustomer(Customer customer);


}
