package org.programmers.kdt.weekly.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.programmers.kdt.weekly.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private static CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerType customerType, String name) {
        Customer customer;
        try {
            customer = new Customer(UUID.randomUUID(), name, customerType);
            customerRepository.insert(customer);
        } catch (IllegalArgumentException e) {
            throw e;
        }
        return customer;
    }

    public Optional<List<Customer>> blackList() {
        return Optional.ofNullable(customerRepository.findAll());
    }

}
