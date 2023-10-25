package devcourse.springbootbasic.repository.customer;

import devcourse.springbootbasic.domain.customer.Customer;

import java.util.List;

public interface CustomerRepository {

    List<Customer> findAllBlacklistedCustomers();
}
