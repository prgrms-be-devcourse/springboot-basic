package org.prgrms.vouchermanager.repository.customer;

import org.prgrms.vouchermanager.domain.customer.Customer;

import java.util.List;

public interface BlacklistCustomerRepository {
    List<Customer> findAll();
}
