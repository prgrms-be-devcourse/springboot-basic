package me.kimihiqq.vouchermanagement.domain.customer.service;

import me.kimihiqq.vouchermanagement.domain.customer.Customer;

import java.util.List;

public interface CustomerService {

    Customer checkCustomer(String customerId);

    List<Customer> getBlacklistedCustomers();

}
