package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.Customer;

import java.util.List;

public interface CustomerRepository {

    List<Customer> readFile(String filePath);
}
