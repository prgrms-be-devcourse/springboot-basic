package com.programmers.springweekly.repository.customer;

import com.programmers.springweekly.domain.customer.Customer;

import java.util.Map;
import java.util.UUID;

public interface CustomerRepository {

    Map<UUID, Customer> getCustomerTypeBlackList();
}
