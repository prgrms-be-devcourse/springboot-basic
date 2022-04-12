package com.prgms.management.customer.service;

import com.prgms.management.customer.entity.Customer;
import com.prgms.management.customer.exception.CustomerException;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers() throws CustomerException;
}
