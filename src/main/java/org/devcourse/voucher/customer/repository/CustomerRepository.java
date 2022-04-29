package org.devcourse.voucher.customer.repository;

import org.devcourse.voucher.customer.model.Customer;

import java.util.List;

public interface CustomerRepository {
    Customer insert(Customer customer);

    List<Customer> findAll();
}
