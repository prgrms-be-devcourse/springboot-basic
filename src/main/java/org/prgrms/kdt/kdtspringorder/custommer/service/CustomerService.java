package org.prgrms.kdt.kdtspringorder.custommer.service;

import org.prgrms.kdt.kdtspringorder.custommer.domain.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> getCustomers();

    Customer getCustomer(UUID customerId);

    UUID createCustomer(Customer customer);

    void createCustomers(List<Customer> customers);

    UUID updateCustomer(Customer customer);

    int deleteCustomer(UUID customerId);

}
