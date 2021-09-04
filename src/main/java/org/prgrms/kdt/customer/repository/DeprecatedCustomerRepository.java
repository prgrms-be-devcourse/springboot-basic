package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.DeprecatedCustomer;

import java.util.List;
import java.util.UUID;

public interface DeprecatedCustomerRepository {

    boolean isInBlacklist(UUID blacklistUUID);
    List<DeprecatedCustomer> getBlacklist();
    List<DeprecatedCustomer> getCustomers();
}
