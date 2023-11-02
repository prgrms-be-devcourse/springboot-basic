package com.prgrms.vouchermanager.repository.customer;

import com.prgrms.vouchermanager.domain.customer.Customer;

import java.util.List;

public interface BlacklistRepository {
    List<Customer> findBlacklist();
}
