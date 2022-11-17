package org.prgrms.kdt.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerStorage {

    void insert(Customer customer);

    Optional<Customer> findById(String customerId);

    List<Customer> findAll();

    void deleteCustomerById(String customerId);

    void update(Customer customer);

}
