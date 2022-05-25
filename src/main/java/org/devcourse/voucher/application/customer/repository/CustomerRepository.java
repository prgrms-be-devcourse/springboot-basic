package org.devcourse.voucher.application.customer.repository;

import org.devcourse.voucher.application.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerRepository {
    Customer insert(Customer customer);

    Page<Customer> findAll(Pageable pageable);

    Customer update(Customer customer);

    void deleteAll();
}
