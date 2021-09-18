package org.prgrms.devcourse.customer;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer join(String name, String email) {
        var newCustomer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now());
        return customerRepository.save(newCustomer);
    }

    @Override
    public Customer changeCustomerInfo(Customer customer) {
        return customerRepository.update(customer);
    }

    @Override
    public int getCustomerCount() {
        return customerRepository.count();
    }

    @Override
    public Optional<Customer> getCustomer(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void removeAllCustomer() {

    }
}
