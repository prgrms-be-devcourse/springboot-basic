package me.kimihiqq.vouchermanagement.domain.customer.repository;

import me.kimihiqq.vouchermanagement.domain.customer.Customer;

import java.util.List;

public interface CustomerRepository {

    Customer findCustomer(String customerId);

    List<Customer> findBlacklistedCustomers();

}