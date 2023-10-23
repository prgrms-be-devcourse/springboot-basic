package org.prgrms.vouchermanager.repository;

import org.prgrms.vouchermanager.domain.customer.Customer;

import java.util.List;

public interface CustomerRepositroy {
    List<Customer> findAll();

    Customer save(Customer customer);
}
