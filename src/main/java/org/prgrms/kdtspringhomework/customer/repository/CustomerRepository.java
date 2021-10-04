package org.prgrms.kdtspringhomework.customer.repository;

import org.prgrms.kdtspringhomework.customer.domain.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();
}
