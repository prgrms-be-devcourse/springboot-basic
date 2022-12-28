package com.programmers.assignment.voucher.engine.repository;

import com.programmers.assignment.voucher.engine.model.Customer;
import com.programmers.assignment.voucher.util.dto.CustomerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Customer update(CustomerDto customerDto, Long customerId);

    List<Customer> findAll();

    Optional<Customer> findById(Long customerId);

    Optional<Customer> findByUuid(UUID customerUuid);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    void deleteAll();

    int count();
}
