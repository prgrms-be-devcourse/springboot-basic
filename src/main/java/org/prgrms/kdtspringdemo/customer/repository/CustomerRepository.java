package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.domain.Customer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);
    void deleteAll();
    void deleteById(UUID customerId);
    List<Customer> findAll();
    List<Customer> findNotHaveWalletCustomers();
    List<Customer> getAllBlackList();
}
