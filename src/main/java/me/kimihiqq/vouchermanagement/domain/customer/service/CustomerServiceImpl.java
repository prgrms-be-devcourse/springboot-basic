package me.kimihiqq.vouchermanagement.domain.customer.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.customer.Customer;
import me.kimihiqq.vouchermanagement.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public Customer checkCustomer(String customerId) {
        Customer customer = customerRepository.findCustomer(customerId);
        log.info("Checked customer with ID {}: {}", customerId, customer);
        return customer;
    }

    public List<Customer> getBlacklistedCustomers() {
        List<Customer> blacklistedCustomers = customerRepository.findBlacklistedCustomers();
        log.info("Got {} blacklisted customers", blacklistedCustomers.size());
        return blacklistedCustomers;
    }
}
