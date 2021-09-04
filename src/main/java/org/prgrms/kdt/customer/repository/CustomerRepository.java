package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository {

    boolean isInBlacklist(UUID blacklistUUID);
    List<Customer> getBlacklist();
    List<Customer> getCustomers();
}
