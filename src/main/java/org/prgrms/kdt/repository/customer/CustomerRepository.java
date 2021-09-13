package org.prgrms.kdt.repository.customer;

import java.util.*;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.CustomerType;

public interface CustomerRepository {

    List<Customer> findByCustomerType(CustomerType customerType);

    Optional<Customer> findById(UUID customerId);

    Customer insert(Customer customer);

    List<Customer> findAllCustomer();

    Customer updateType(Customer customer);

    void deleteAllCustomer();

    List<Customer> findByVoucherId(UUID voucherId);

}
