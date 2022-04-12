package org.prgrms.kdt.domain.customer.repository;

import org.prgrms.kdt.domain.customer.model.Customer;

import java.util.List;

public interface CustomerRepository {

    List<Customer> findAll();
}
