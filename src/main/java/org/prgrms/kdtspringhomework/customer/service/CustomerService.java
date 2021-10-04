package org.prgrms.kdtspringhomework.customer.service;

import org.prgrms.kdtspringhomework.customer.domain.Customer;
import org.prgrms.kdtspringhomework.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> listBlackCustomers() {
        return customerRepository.findAll();
    }

    public void printCustomers() {
        listBlackCustomers().stream()
                .map(Customer::toString)
                .forEach(System.out::println);
    }
}
