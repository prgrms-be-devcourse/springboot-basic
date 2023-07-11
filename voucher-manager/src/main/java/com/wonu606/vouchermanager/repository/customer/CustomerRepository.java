package com.wonu606.vouchermanager.repository.customer;

import com.wonu606.vouchermanager.domain.customer.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findByEmailAddress(String emailAddress);

    List<Customer> findAll();

    void deleteByEmailAddress(String emailAddress);

    void deleteAll();
}
