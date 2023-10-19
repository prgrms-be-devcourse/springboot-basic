package com.programmers.vouchermanagement.customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findBlackCustomers();
}
