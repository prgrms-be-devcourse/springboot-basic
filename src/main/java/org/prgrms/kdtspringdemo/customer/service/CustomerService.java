package org.prgrms.kdtspringdemo.customer.service;

import org.prgrms.kdtspringdemo.customer.exception.CustomerException;
import org.prgrms.kdtspringdemo.customer.model.dto.CustomerResponse;
import org.prgrms.kdtspringdemo.customer.model.entity.Customer;
import org.prgrms.kdtspringdemo.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

        return new CustomerResponse(savedCustomer.getId(), savedCustomer.getNickname());
    }

    public CustomerResponse findById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerException(CUSTOMER_ID_LOOKUP_FAILED));

        return new CustomerResponse(customer.getId(), customer.getNickname());
    }

    public CustomerResponse findByNickname(String nickname) {
        Customer customer = customerRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomerException(CUSTOMER_NICKNAME_LOOKUP_FAILED));

        return new CustomerResponse(customer.getId(), customer.getNickname());
    }

    public List<CustomerResponse> findAll() {
        return customerRepository.findAll().stream()
                .map(v -> new CustomerResponse(v.getId(), v.getNickname()))
                .collect(Collectors.toUnmodifiableList());
    }

    public CustomerResponse update(UUID id, String nickname) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerException(CUSTOMER_ID_LOOKUP_FAILED));
        customer.update(nickname);
        customerRepository.update(customer);

        return new CustomerResponse(customer.getId(), customer.getNickname());
    }

    public void delete(UUID customerId) {
        customerRepository.deleteById(customerId);
    }
}
