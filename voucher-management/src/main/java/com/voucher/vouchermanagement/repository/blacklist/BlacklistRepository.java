package com.voucher.vouchermanagement.repository.blacklist;

import com.voucher.vouchermanagement.model.customer.Customer;

import java.util.List;


public interface BlacklistRepository {
    List<Customer> findAll();
}
