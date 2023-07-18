package org.prgrms.kdtspringdemo.customer.service;

import org.prgrms.kdtspringdemo.customer.exception.CustomerIdNotFoundException;
import org.prgrms.kdtspringdemo.customer.exception.CustomerNicknameNotFoundException;
import org.prgrms.kdtspringdemo.customer.model.dto.CustomerResponseDto;
import org.prgrms.kdtspringdemo.customer.model.entity.Customer;
import org.prgrms.kdtspringdemo.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.prgrms.kdtspringdemo.customer.exception.CustomerExceptionMessage.*;

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
        Customer customer = validateExist(customerRepository.findById(customerID));

        return CustomerResponseDto.toDto(customer.getCustomerId(), customer.getNickname());
    }

    private static Customer validateExist(Optional<Customer> foundCustomer) {
        return foundCustomer.orElseThrow(() -> new CustomerIdNotFoundException(CUSTOMER_ID_LOOKUP_FAILED));
    }

    public CustomerResponseDto findByNickname(String nickname) {
        Optional<Customer> foundCustomer = customerRepository.findByNickname(nickname);
        Customer customer = foundCustomer.orElseThrow(() -> new CustomerNicknameNotFoundException(CUSTOMER_NICKNAME_LOOKUP_FAILED));

        return CustomerResponseDto.toDto(customer.getCustomerId(), customer.getNickname());
    }

    public List<CustomerResponseDto> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(v -> new CustomerResponseDto(v.getCustomerId(), v.getNickname()))
                .collect(Collectors.toUnmodifiableList());
    }

    public CustomerResponseDto update(UUID customerId, String nickname) {
        Customer customer = validateExist(customerRepository.findById(customerId));
        customer.update(nickname);
        customerRepository.update(customer);

        return CustomerResponseDto.toDto(customer.getCustomerId(), customer.getNickname());
    }

    public void delete(UUID customerId) {
        customerRepository.deleteById(customerId);
    }
}
