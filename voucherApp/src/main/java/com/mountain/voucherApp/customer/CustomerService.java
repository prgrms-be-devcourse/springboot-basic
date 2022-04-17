package com.mountain.voucherApp.customer;

import java.util.List;

public interface CustomerService {
    void createCustomers(List<Customer> customers);
    List<Customer> findAll();
}
