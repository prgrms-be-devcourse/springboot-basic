package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.customer.Customer;

import java.util.List;

public interface CustomerRepository {

    List<Customer> findAll();

}
