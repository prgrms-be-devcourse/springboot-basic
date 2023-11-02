package com.prgms.vouchermanager.repository.customer;

import com.prgms.vouchermanager.domain.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);

    void update(Customer customer);


    Optional<Customer> findById(Long id);

    List<Customer> findAll();

    void deleteById(Long id);

    void deleteAll();

    List<Customer> findBlackList();

}
