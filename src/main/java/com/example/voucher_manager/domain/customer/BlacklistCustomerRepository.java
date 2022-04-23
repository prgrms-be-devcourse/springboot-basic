package com.example.voucher_manager.domain.customer;

import java.util.List;

public interface BlacklistCustomerRepository {
    List<Customer> findAll();
}
