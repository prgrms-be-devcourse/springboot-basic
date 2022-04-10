package com.prgrms.management.customer.repository;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerType;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);
    List<Customer> findBlackList();
}
