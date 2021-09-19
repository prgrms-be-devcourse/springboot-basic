package org.prgrms.kdt.customer.service;

import java.util.*;
import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.prgrms.kdt.customer.model.Customer;
import org.prgrms.kdt.customer.model.CustomerType;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomer(UUID customerId) {
        return customerRepository
            .findById(customerId)
            .orElseThrow(
                () -> new RuntimeException("Can not find customer %s".formatted(customerId)));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAllCustomer();
    }

    public List<Customer> getAllBlackCustomers() {
        return customerRepository.findByCustomerType(CustomerType.BLACK);
    }

    public List<Customer> getCustomersByVoucherId(UUID voucherId) {
        return customerRepository.findByVoucherId(voucherId);
    }
}
