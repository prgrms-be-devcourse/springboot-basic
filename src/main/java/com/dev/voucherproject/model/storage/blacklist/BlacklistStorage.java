package com.dev.voucherproject.model.storage.blacklist;

import com.dev.voucherproject.model.customer.Customer;

import java.util.List;

public interface BlacklistStorage {
    List<Customer> findAll();
}
