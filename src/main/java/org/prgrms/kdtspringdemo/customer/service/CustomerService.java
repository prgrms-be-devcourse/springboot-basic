package org.prgrms.kdtspringdemo.customer.service;

import org.prgrms.kdtspringdemo.customer.model.dto.CustomerResponseDto;
import org.prgrms.kdtspringdemo.customer.model.entity.Customer;
import org.prgrms.kdtspringdemo.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponseDto create(String nickname) {
        Customer savedCustomer = customerRepository.save(new Customer(nickname));

        return CustomerResponseDto.toDto(savedCustomer.getCustomerId(), savedCustomer.getNickname());
    }

    public CustomerResponseDto findById(UUID customerID) {
        Customer customer = customerRepository.findById(customerID);

        return CustomerResponseDto.toDto(customer.getCustomerId(), customer.getNickname());
    }

    public CustomerResponseDto findByNickname(String nickname) {
        Customer customer = customerRepository.findByNickname(nickname);

        return CustomerResponseDto.toDto(customer.getCustomerId(), customer.getNickname());
    }

    public List<CustomerResponseDto> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(v -> new CustomerResponseDto(v.getCustomerId(), v.getNickname()))
                .collect(Collectors.toUnmodifiableList());
    }

    public CustomerResponseDto update(UUID customerId, String nickname) {
        Customer customer = customerRepository.findById(customerId);
        customer.update(nickname);

        Customer updatedCustomer = customerRepository.update(customer);

        return CustomerResponseDto.toDto(updatedCustomer.getCustomerId(), updatedCustomer.getNickname());
    }

    public CustomerResponseDto delete(UUID customerId) {
        Customer customer = customerRepository.deleteById(customerId);

        return CustomerResponseDto.toDto(customer.getCustomerId(), customer.getNickname());
    }
}
