package com.mountain.voucherApp.customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> findAll();
    List<Customer> findByVoucherId(UUID voucherId);
    Customer update(Customer customer);
    List<Customer> findByVoucherIdNotNull();
    void removeByCustomerId(UUID customerId);
}
