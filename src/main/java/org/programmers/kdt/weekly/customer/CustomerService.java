package org.programmers.kdt.weekly.customer;

import java.util.List;
import java.util.UUID;
import org.programmers.kdt.weekly.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerType customerType, String name) {
        Customer customer = new Customer(UUID.randomUUID(), name, customerType);
        this.customerRepository.insert(customer);

        return customer;
    }

    public List<Customer> getBlackList() {
        return this.customerRepository.findAll();
    }

}