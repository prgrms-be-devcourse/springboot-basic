package org.prgrms.voucher.service;


import org.prgrms.voucher.models.Customer;
import org.prgrms.voucher.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }

    public Customer create(Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException();
        }

        return customerRepository.save(customer);
    }

    public List<Customer> getCustomers() {

        return customerRepository.findAll();
    }

    public List<Customer> getCustomersByTerm(LocalDate after, LocalDate before) {

        return customerRepository.findByTerm(after, before);
    }

    public Customer getCustomer(Long customerId) {

        return customerRepository.findById(customerId).orElseThrow(IllegalArgumentException::new);
    }

    public void deleteCustomerById(Long customerId) {

        customerRepository.findById(customerId).orElseThrow(IllegalArgumentException::new);
        customerRepository.deleteById(customerId);
    }
}