package org.prgrms.devcourse.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer join(String name, String email);

    Customer changeCustomerInfo(Customer customer);

    int getCustomerCount();

    Optional<Customer> getCustomer(String email);

    List<Customer> getAllCustomers();

    void removeAllCustomer();
}
