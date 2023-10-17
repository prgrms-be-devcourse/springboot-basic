package org.prgms.kdtspringweek1.customer.repository;

import org.prgms.kdtspringweek1.customer.entity.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAllBlackConsumer();
}
