package org.prgrms.part1.engine.service;

import org.prgrms.part1.engine.domain.Customer;
import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.engine.repository.CustomerRepository;
import org.prgrms.part1.exception.VoucherException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(UUID customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new VoucherException("Invalid customer id.");
        }
        return customer.get();
    }

    public Customer createCustomer(String name, String email) {
        return insertCustomer(new Customer(UUID.randomUUID(), name, email, LocalDateTime.now().withNano(0)));
    }

    public Customer insertCustomer(Customer customer) {
        return customerRepository.insert(customer);
    }
}
