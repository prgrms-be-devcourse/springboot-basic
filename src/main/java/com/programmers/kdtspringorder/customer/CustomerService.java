package com.programmers.kdtspringorder.customer;

import com.programmers.kdtspringorder.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    void createCustomers(List<Customer> customers);

    Customer insert(Customer customer);

    Optional<Customer> findById(UUID customerId);

    List<Customer> findAll();

}
