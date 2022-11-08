package org.prgrms.vouchermanagement.customer.repository;

import org.prgrms.vouchermanagement.customer.domain.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();
}
