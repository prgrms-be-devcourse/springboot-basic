package org.prgrms.kdt.customer.service;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerByEmail(final String email) {
        return customerRepository.findByEmail(email).get();
    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Transactional()
    public void createCustomers(final List<Customer> customers) {
        customers.forEach(customerRepository::insert);
    }

    public void addCustomer(final Customer customer) {
        customerRepository.insert(customer);
    }

}
