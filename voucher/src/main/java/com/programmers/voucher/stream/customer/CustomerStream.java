package com.programmers.voucher.stream.customer;

import com.programmers.voucher.domain.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerStream {
    List<Customer> findAll();

    Optional<Customer> findById(String customerId);

    String save(Customer customer);

    String update(String customerId, String name);

    void deleteById(String customerId);

    void deleteAll();

}
