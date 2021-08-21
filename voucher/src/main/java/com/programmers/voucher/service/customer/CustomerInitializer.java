package com.programmers.voucher.service.customer;

import com.programmers.voucher.entity.customer.Customer;

import java.util.List;

public interface CustomerInitializer {
    void loadCustomers();
    List<Customer> readCustomers();
}
