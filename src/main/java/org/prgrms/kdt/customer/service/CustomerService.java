package org.prgrms.kdt.customer.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.prgrms.kdt.customer.model.Customer;
import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(
        CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(UUID id) {
        return customerRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);
    }

    public Customer makeCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomers() {
        customerRepository.deleteAll();
    }

}
