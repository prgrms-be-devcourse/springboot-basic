package org.prgrms.vouchermanagement.customer.service;

import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> getCustomerList() {
    return customerRepository.findAll();
  }
}
