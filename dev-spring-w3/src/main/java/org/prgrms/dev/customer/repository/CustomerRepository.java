package org.prgrms.dev.customer.repository;

import org.prgrms.dev.customer.domain.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAllInBlackList();
}
