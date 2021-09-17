package org.prgrms.kdtspringdemo.customer.service;

import org.prgrms.kdtspringdemo.customer.Customer;
import org.prgrms.kdtspringdemo.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllBlacklist() {
        return customerRepository.findBlacklist();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(String name, String email, String type) {
        Customer customer = customerRepository.insert(
                new Customer(UUID.randomUUID(),
                        name,
                        email,
                        type,
                        LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)));

        if (customer == null) {
            logger.error(MessageFormat.format("Invalid create command. Your input -> {0}, {1}, {2}", name, email, type));
            System.out.println("[ERROR]Invalid create command");
            System.out.println(MessageFormat.format("Your input : {0}, {1}, {2}", name, email, type));
        }

        return customer;
    }

    public void deleteCustomer(String customerId) {
        customerRepository.deleteByCustomerId(customerId);
    }

    public Customer findCustomer(String voucherId) {
        Optional<Customer> customer = customerRepository.findByVoucherId(voucherId);
        return customer.orElse(null);
    }
}
