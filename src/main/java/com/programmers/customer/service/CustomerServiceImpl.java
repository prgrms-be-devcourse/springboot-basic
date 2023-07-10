package com.programmers.customer.service;

import com.programmers.customer.domain.Customer;
import com.programmers.customer.domain.CustomerMapper;
import com.programmers.customer.dto.CustomerRequestDto;
import com.programmers.customer.dto.CustomerResponseDto;
import com.programmers.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public CustomerResponseDto create(CustomerRequestDto requestDto) {
        Customer customer = CustomerMapper.convertRequestDtoToDomain(requestDto);
        return CustomerMapper.convertDomainToResponseDto(customerRepository.save(customer));
    }

    @Transactional
    @Override
    public CustomerResponseDto update(CustomerRequestDto requestDto) {
        Customer oldCustomer = customerRepository.findById(requestDto.customerId());
        UUID id = oldCustomer.getCustomerId();
        String newName = requestDto.name();
        LocalDateTime createdAt = oldCustomer.getCreatedAt();
        LocalDateTime modifiedAt = LocalDateTime.now();
        Customer newCustomer = new Customer(id, newName, createdAt, modifiedAt);
        return CustomerMapper.convertDomainToResponseDto(customerRepository.update(newCustomer));
    }

    @Override
    public List<CustomerResponseDto> findCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::convertDomainToResponseDto)
                .toList();
    }

    @Override
    public CustomerResponseDto findCustomerById(UUID customerID) {
        Customer customer = customerRepository.findById(customerID);
        return CustomerMapper.convertDomainToResponseDto(customer);
    }

    @Transactional
    @Override
    public void deleteCustomerById(UUID customerID) {
        customerRepository.deleteById(customerID);
    }
}
