package com.prgms.management.customer.repository;

import com.prgms.management.customer.entity.Customer;
import com.prgms.management.customer.exception.CustomerException;

import java.util.List;

public interface BlackCustomerRepository {
    List<Customer> findAll() throws CustomerException;

}
