package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.Customer;

import java.util.List;

public interface CustomerRepository {

    Customer insert(String name, String email);

    void update(Long customerId, String name, String email);

    List<Customer> findAll();

    Customer findById(Long customerId);

    void deleteAll();

}
