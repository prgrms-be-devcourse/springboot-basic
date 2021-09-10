package org.prgrms.kdt.repository;

import java.util.*;
import org.prgrms.kdt.model.Customer;
import org.prgrms.kdt.model.CustomerType;

public interface CustomerRepository {

    List<Customer> findByCustomerType(CustomerType customerType);

    Optional<Customer> findById(UUID customerId);

    Customer insert(Customer customer);

    List<Customer> findAllCustomer();

    Customer updateType(Customer customer);

    void deleteAllCustomer();

}
