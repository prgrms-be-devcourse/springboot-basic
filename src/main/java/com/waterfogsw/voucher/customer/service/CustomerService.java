package com.waterfogsw.voucher.customer.service;

import com.waterfogsw.voucher.customer.model.Customer;

import java.util.List;

public interface CustomerService {
    void addCustomer(Customer customer);

    List<Customer> findAllCustomer();

    Customer findById(long id);

    void deleteById(long id);
}
