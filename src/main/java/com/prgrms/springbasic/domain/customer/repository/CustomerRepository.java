package com.prgrms.springbasic.domain.customer.repository;


import com.prgrms.springbasic.domain.customer.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    List<Customer> findAllBlackList();

    Optional<Customer> findCustomerById(UUID customerId);

    Optional<Customer> findCustomerByEmail(String email);

    List<Customer> findAll();
}
