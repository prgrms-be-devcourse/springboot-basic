package com.programmers.springweekly.repository.customer;

import com.programmers.springweekly.domain.customer.Customer;
import java.util.List;

public interface CustomerRepository {

    List<Customer> getBlackList();
}
