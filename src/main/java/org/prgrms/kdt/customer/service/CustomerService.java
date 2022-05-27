package org.prgrms.kdt.customer.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.prgrms.kdt.customer.model.Customer;
import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.prgrms.kdt.error.CustomerNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(
        CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return this.customerRepository.findAll();
    }

    public Customer getCustomer(UUID id) {
        return this.customerRepository.findById(id)
            .orElseThrow(CustomerNotFoundException::new);
    }

    public Customer makeCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public void deleteCustomers() {
        this.customerRepository.deleteAll();
    }

}
