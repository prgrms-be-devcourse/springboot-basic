package com.prgrms.voucher_manager.customer.repository;

import com.prgrms.voucher_manager.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Integer count();

    List<Customer> findAll();

    Customer update(Customer customer);

    Customer delete(Customer customer);

    Optional<Customer> findById(UUID customerId);

}

