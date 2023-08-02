package com.prgrms.custoemer.repository;

import com.prgrms.custoemer.model.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(String customerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    boolean existsById(String voucher_id);

}
