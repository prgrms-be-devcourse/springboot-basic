package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.dto.CustomerDTO;
import com.programmers.springbootbasic.repository.CustomerJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    public CustomerService(CustomerJdbcRepository customerJdbcRepository) {
        this.customerJdbcRepository = customerJdbcRepository;
    }

    public CustomerDTO createCustomer(String customerId, String name) {
        return customerJdbcRepository.insert(new CustomerDTO(customerId, name));
    }

    public Optional<CustomerDTO> findCustomerById(String customerId) {
        return customerJdbcRepository.findById(customerId);
    }

    public List<CustomerDTO> findAllCustomers() {
        return customerJdbcRepository.findAll();
    }

    public void deleteCustomerById(String customerId) {
        customerJdbcRepository.deleteById(customerId);
    }

    public void deleteAllCustomers() {
        customerJdbcRepository.deleteAll();
    }

}