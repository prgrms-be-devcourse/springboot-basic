package org.devcourse.voucher.customer.repository;

import org.devcourse.voucher.customer.model.Customer;

import java.util.List;

public interface BlacklistRepository {
    List<Customer> findAll();
}
