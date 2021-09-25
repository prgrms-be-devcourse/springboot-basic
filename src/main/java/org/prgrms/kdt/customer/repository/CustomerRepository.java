package org.prgrms.kdt.customer.repository;

import java.util.*;
import org.prgrms.kdt.customer.model.Customer;
import org.prgrms.kdt.customer.model.CustomerType;

public interface CustomerRepository {

    List<Customer> findByCustomerType(CustomerType customerType);

    Optional<Customer> findById(UUID customerId);

    Customer insert(Customer customer);

    List<Customer> findAllCustomer();

    Customer update(Customer customer);

    void deleteAllCustomer();

    void deleteById(UUID customerId);

    List<Customer> findByVoucherId(UUID voucherId);

}
