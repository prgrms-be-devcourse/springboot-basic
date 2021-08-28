package com.programmers.voucher.service.customer;

import com.programmers.voucher.entity.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    void openStorage();
    void closeStorage();

    Customer create(String username, String alias);
    Optional<Customer> findById(long id);
    List<Customer> listAll();
}
