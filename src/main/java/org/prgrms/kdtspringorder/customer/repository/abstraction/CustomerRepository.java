package org.prgrms.kdtspringorder.customer.repository.abstraction;

import org.prgrms.kdtspringorder.customer.domain.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> getBannedCustomers();
}
