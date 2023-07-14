package com.prgrms.commandLineApplication.service;

import com.prgrms.commandLineApplication.customer.Customer;
import com.prgrms.commandLineApplication.customer.dto.CustomerCreateDto;
import com.prgrms.commandLineApplication.customer.dto.CustomerMapper;
import com.prgrms.commandLineApplication.customer.dto.CustomerResponseDto;
import com.prgrms.commandLineApplication.customer.dto.CustomerUpdateDto;
import com.prgrms.commandLineApplication.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
    this.customerRepository = customerRepository;
    this.customerMapper = customerMapper;
  }

  public UUID create(CustomerCreateDto customerCreateDto) {
    Customer createdCustomer = Customer.of(UUID.randomUUID(), customerCreateDto.customerName(), customerCreateDto.email());
    customerRepository.save(createdCustomer);
    return createdCustomer.getCustomerId();
  }

  public String update(CustomerUpdateDto customerUpdateDto) {
    customerRepository.findByEmail(customerUpdateDto.email());
    customerRepository.update(customerUpdateDto.email(), customerUpdateDto.customerNewName());
    return customerUpdateDto.email();
  }

  public List<CustomerResponseDto> findAllCustomers() {
    return customerRepository.findAll()
            .stream()
            .map(customerMapper::mapToResponse)
            .toList();
  }

  public CustomerResponseDto findById(UUID customerId) {
    Customer customer = customerRepository.findById(customerId);
    CustomerResponseDto customerResponseDto = customerMapper.mapToResponse(customer);
    return customerResponseDto;
  }

  public CustomerResponseDto findByEmail(String email) {
    Customer customer = customerRepository.findByEmail(email);
    CustomerResponseDto customerResponseDto = customerMapper.mapToResponse(customer);
    return customerResponseDto;
  }

  public void deleteAll() {
    customerRepository.deleteAll();
  }

  public void deleteById(UUID customerId) {
    customerRepository.deleteById(customerId);
  }

}
