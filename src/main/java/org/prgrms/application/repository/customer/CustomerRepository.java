package org.prgrms.application.repository.customer;

import org.prgrms.application.domain.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Customer update(Customer customer);

    //    int count();
    //  Customer save(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(Long customerId);
    Optional<Customer> findByName(String customerId);
    Optional<Customer> findByEmail(String customerId);

    void deleteAll();

}
