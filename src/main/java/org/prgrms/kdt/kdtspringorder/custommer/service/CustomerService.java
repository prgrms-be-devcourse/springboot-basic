package org.prgrms.kdt.kdtspringorder.custommer.service;

import org.prgrms.kdt.kdtspringorder.custommer.domain.Customer;

import java.util.List;

public interface CustomerService {
    void createCustomers(List<Customer> customers);
}
