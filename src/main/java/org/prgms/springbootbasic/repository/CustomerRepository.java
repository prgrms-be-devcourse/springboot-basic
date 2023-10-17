package org.prgms.springbootbasic.repository;

import org.prgms.springbootbasic.domain.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findBlackAll();
}
