package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Customer;

import java.util.List;

public interface BlacklistRepository {
    List<Customer> blacklist();
}
