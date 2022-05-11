package org.prgrms.kdt.repository.customer;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.CustomerType;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();
    List<Customer> findAllByCustomerType(CustomerType customerType);
    Customer insert(Customer customer);
    void deleteAll();
}
