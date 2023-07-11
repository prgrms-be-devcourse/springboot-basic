package com.prgrms.commandLineApplication.service;

import com.prgrms.commandLineApplication.customer.Customer;
import com.prgrms.commandLineApplication.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public void create(String customerName, String email) {
    Customer createdCustomer = Customer.of(UUID.randomUUID(), customerName, email);
    customerRepository.save(createdCustomer);
  }

  public UUID update(UUID customerId, String customerName) {
    Customer customer = customerRepository.findById(customerId);
    return customerRepository.update(customerId, customerName);
  }

  public List<Customer> findAllCustomers() {
    return customerRepository.findAll();
  }

  public Customer findById(UUID customerId) {
    return customerRepository.findById(customerId);
  }

  public Customer findByEmail(String email) {
    return customerRepository.findByEmail(email);
  }

  public void deleteAll() {
    customerRepository.deleteAll();
  }

  public void deleteById(UUID customerId) {
    customerRepository.deleteById(customerId);
  }

}
