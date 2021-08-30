package org.programmers.customer;

import java.util.List;

public interface CustomerRepository {

    List<Customer> getAllCustomersOnBlacklist();

}
