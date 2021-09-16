package com.programmers.voucher.repository.customer;

import com.programmers.voucher.entity.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    void loadCustomers();

    void persistCustomers();

    Customer save(Customer customer);

    Optional<Customer> findById(long id);

    Optional<Customer> findByVoucher(long voucherId);

    List<Customer> listAll();
}
