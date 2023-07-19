package org.prgrms.kdtspringdemo.customer.service;

import org.prgrms.kdtspringdemo.customer.exception.CustomerIdNotFoundException;
import org.prgrms.kdtspringdemo.customer.exception.CustomerNicknameNotFoundException;
import org.prgrms.kdtspringdemo.customer.model.dto.CustomerResponse;
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

    public CustomerResponse create(String nickname) {
        Customer savedCustomer = customerRepository.save(new Customer(nickname));

        return CustomerResponse.toDto(savedCustomer.getCustomerId(), savedCustomer.getNickname());
    }

    public CustomerResponse findById(UUID customerID) {
        Customer customer = validateExist(customerRepository.findById(customerID));

        return CustomerResponse.toDto(customer.getCustomerId(), customer.getNickname());
    }

    private static Customer validateExist(Optional<Customer> foundCustomer) {
        return foundCustomer.orElseThrow(() -> new CustomerIdNotFoundException(CUSTOMER_ID_LOOKUP_FAILED));
    }

    public CustomerResponse findByNickname(String nickname) {
        Optional<Customer> foundCustomer = customerRepository.findByNickname(nickname);
        Customer customer = foundCustomer.orElseThrow(() -> new CustomerNicknameNotFoundException(CUSTOMER_NICKNAME_LOOKUP_FAILED));

        return CustomerResponse.toDto(customer.getCustomerId(), customer.getNickname());
    }

    public List<CustomerResponse> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(v -> new CustomerResponse(v.getCustomerId(), v.getNickname()))
                .collect(Collectors.toUnmodifiableList());
    }

    public CustomerResponse update(UUID customerId, String nickname) {
        Customer customer = validateExist(customerRepository.findById(customerId));
        customer.update(nickname);
        customerRepository.update(customer);

        return CustomerResponse.toDto(customer.getCustomerId(), customer.getNickname());
    }

    public void delete(UUID customerId) {
        customerRepository.deleteById(customerId);
    }
}
