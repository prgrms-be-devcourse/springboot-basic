package org.programs.kdt.Customer;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Exception.DuplicationException;
import org.programs.kdt.Exception.EntityNotFoundException;
import org.programs.kdt.Exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  public List<Customer> findAllBlackList() {
    return customerRepository.findByType("blacklist");
  }

  public Customer save(Customer customer) {
    boolean isId = customerRepository.existId(customer.getCustomerId());
    boolean isEmail = customerRepository.existEmail(customer.getEmail());
    if (!(isEmail || isId)) {
      return customerRepository.insert(customer);
    } else if(isEmail) {
      throw new DuplicationException(ErrorCode.DUPLICATION_CUSTOMER_EMAIL);
    }else {
      throw new DuplicationException(ErrorCode.DUPLICATION_CUSTOMER_ID);
    }
  }

  public List<Customer> findAll() {
    return customerRepository.findAll();
  }

  public Optional<Customer> findByEmail(String email) {
      return customerRepository.findByEmail(email);
  }

  public void deleteByEmail(String email) {
    boolean isEmail = customerRepository.existEmail(email);
    if (isEmail) {
      customerRepository.deleteByEmail(email);
    } else {
      throw new EntityNotFoundException(ErrorCode.DUPLICATION_CUSTOMER_EMAIL);
    }
  }

  public Customer update(Customer customer) {
    boolean isId = customerRepository.existId(customer.getCustomerId());
    if (isId) {
      return customerRepository.update(customer);
    } else {
      throw new EntityNotFoundException(ErrorCode.NOT_FOUND_CUSTOMER_ID);
    }
  }

  public Optional<Customer> findById(UUID customerId) {
      return customerRepository.findById(customerId);
  }

  public boolean existEmail(String email) {
    return customerRepository.existEmail(email);
  }

  public boolean existId(UUID customerId) {
    return customerRepository.existId(customerId);
  }
}
