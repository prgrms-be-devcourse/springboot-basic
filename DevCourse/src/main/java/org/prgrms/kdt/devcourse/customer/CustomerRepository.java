package org.prgrms.kdt.devcourse.customer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface CustomerRepository {
    List<Customer> getBlackCustomers();
}
