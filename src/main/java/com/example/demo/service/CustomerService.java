package com.example.demo.service;

import com.example.demo.domain.customer.Customer;
import com.example.demo.domain.customer.CustomerRepository;
import com.example.demo.dto.CustomerDto;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDto save(String name, int age) {
        Customer customer = new Customer(name, age);
        customerRepository.save(customer);
        return CustomerDto.from(customer);
    }

    public CustomerDto read(UUID id) {
        return CustomerDto.from(customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id에 해당하는 customer가 없습니다.")));
    }

    public List<CustomerDto> readAll() {
        List<Customer> customerList = customerRepository.findAll();

        return customerList.stream()
                .map(CustomerDto::from)
                .toList();
    }

    public void updateName(UUID id, String name) {
        customerRepository.updateName(id, name);
    }

    public void deleteById(UUID id) {
        customerRepository.deleteById(id);
    }
}
