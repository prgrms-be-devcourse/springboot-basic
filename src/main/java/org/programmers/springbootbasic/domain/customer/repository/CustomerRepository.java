package org.programmers.springbootbasic.domain.customer.repository;

import org.programmers.springbootbasic.domain.customer.model.Customer;
import org.programmers.springbootbasic.domain.customer.dto.CustomerInsertDto;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    List<Customer> findAll();

    Optional<Customer> findById(long customerId);

    Optional<Customer> findByEmail(String name);

    void insert(CustomerInsertDto customerInsertDto);

    void deleteAll();
}
