package org.prgrms.kdt.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.exception.DuplicatedEmailException;
import org.prgrms.kdt.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository voucherRepository) {
    this.customerRepository = voucherRepository;
  }

  public List<Customer> findAll() {
    return customerRepository.findAll();
  }

  public Optional<Customer> findById(UUID customerId) {
    return customerRepository.findById(customerId);
  }

  public Customer register(String name, String email) {
    validateDuplicated(email);
    var customer = new Customer(name, email);
    return customerRepository.save(customer);
  }

  public Optional<Customer> findCustomerByVoucherId(UUID voucherId) {
    return customerRepository.findCustomerByVoucherId(voucherId);
  }

  private void validateDuplicated(String email) {
    if (customerRepository.existsByEmail(email)) {
      throw new DuplicatedEmailException();
    }
  }
}