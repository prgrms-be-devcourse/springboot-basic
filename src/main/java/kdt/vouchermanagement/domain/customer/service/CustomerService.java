package kdt.vouchermanagement.domain.customer.service;

import kdt.vouchermanagement.domain.customer.domain.Customer;
import kdt.vouchermanagement.domain.customer.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    void saveCustomer(Customer customer);

    List<CustomerResponse> findAllCustomers();

    void deleteById(long id);
}
