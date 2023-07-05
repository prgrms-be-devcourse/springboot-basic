package com.prgms.VoucherApp.domain.customer.storage;

import com.prgms.VoucherApp.domain.customer.Customer;

import java.util.List;

public interface BlackListStorage {

    List<Customer> findAll();
}
