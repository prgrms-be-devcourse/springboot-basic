package com.example.demo.service;

import com.example.demo.domain.customer.Customer;
import com.example.demo.domain.customer.CustomerRepository;
import com.example.demo.dto.customer.CustomerResponseDto;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponseDto save(String name, int age) {
        Customer customer = new Customer(name, age);
        customerRepository.save(customer);
        return CustomerResponseDto.from(customer);
    }

    public CustomerResponseDto read(UUID id) {
        return CustomerResponseDto.from(customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id에 해당하는 customer가 없습니다.")));
    }

    public List<CustomerResponseDto> readAll() {
        List<Customer> customerList = customerRepository.findAll();

        return customerList.stream()
                .map(CustomerResponseDto::from)
                .toList();
    }

    public void updateName(UUID id, String name) {
        customerRepository.updateName(id, name);
    }

    public void deleteById(UUID id) {
        customerRepository.deleteById(id);
    }
}
