package org.voucherProject.voucherProject.customer.service;

import org.voucherProject.voucherProject.customer.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer findById(UUID customerId);

    Customer findByName(String customerName);

    Customer findByEmail(String customerEmail);

    List<Customer> findAll();

    Customer save(Customer customer);

    void deleteAll();
}
