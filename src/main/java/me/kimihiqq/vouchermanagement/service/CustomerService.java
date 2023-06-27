package me.kimihiqq.vouchermanagement.service;

import me.kimihiqq.vouchermanagement.domain.Customer;

import java.util.List;

public interface CustomerService {

    void init();

    Customer checkCustomer(String customerId);

    List<Customer> getBlacklistedCustomers();


}
