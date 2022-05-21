package com.voucher.vouchermanagement.domain.blacklist.repository;

import com.voucher.vouchermanagement.domain.customer.model.Customer;

import java.util.List;


public interface BlacklistRepository {
    List<Customer> findAll();
}
