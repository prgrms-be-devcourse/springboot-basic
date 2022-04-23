package org.prgrms.kdt.repository.customer;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.CustomerGrade;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();
    List<Customer> findAllByCustomerGrade(CustomerGrade customerGrade);
}
