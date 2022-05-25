package org.devcourse.voucher.application.customer.repository;

import org.devcourse.voucher.application.customer.model.Customer;

import java.util.List;

public interface BlacklistRepository {
    List<Customer> findAll();
}
