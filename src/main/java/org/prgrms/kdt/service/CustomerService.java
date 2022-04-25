package org.prgrms.kdt.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.dto.CustomerDto;
import org.prgrms.kdt.exception.DuplicatedEmailException;
import org.prgrms.kdt.exception.EntityNotFoundException;
import org.prgrms.kdt.repository.CustomerRepository;
import org.prgrms.kdt.type.ErrorType;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
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

  public Customer register(@Valid CustomerDto customerDto) {
    validateDuplicated(customerDto.email());
    var customer = new Customer(customerDto);
    return customerRepository.save(customer);
  }

  public Customer findCustomerByVoucherId(UUID voucherId) {
    return customerRepository.findCustomerByVoucherId(voucherId)
        .orElseThrow(() -> new EntityNotFoundException(ErrorType.ENTITY_NOT_FOUND));
  }

  private void validateDuplicated(String email) {
    if (customerRepository.existsByEmail(email)) {
      throw new DuplicatedEmailException();
    }
  }
}