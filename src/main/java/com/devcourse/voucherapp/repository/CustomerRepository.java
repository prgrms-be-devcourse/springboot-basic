package com.devcourse.voucherapp.repository;

import com.devcourse.voucherapp.entity.customer.Customer;

public interface CustomerRepository {

    Customer create(Customer customer);
}
