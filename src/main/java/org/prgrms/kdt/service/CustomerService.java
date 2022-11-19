package org.prgrms.kdt.service;

import org.prgrms.kdt.dao.entity.customer.Customer;
import org.prgrms.kdt.dao.repository.customer.CustomerRepository;
import org.prgrms.kdt.exception.repository.NotPresentInRepositoryException;
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

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllStoredCustomer();
    }

    public List<Customer> getAllBlacklist() {
        return customerRepository.getAllStoredCustomer().stream()
                .filter(Customer::isBlacklist)
                .collect(Collectors.toList());
    }

    public Customer findCustomerById(String customerId) {
        try {
            return customerRepository.findById(UUID.fromString(customerId))
                    .orElseThrow(() -> new NotPresentInRepositoryException("입력된 customer ID가 존재하지 않습니다."));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("입력된 customer ID가 UUID 형식이 아닙니다.", e);
        }
    }
}
