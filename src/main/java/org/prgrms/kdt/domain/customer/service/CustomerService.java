package org.prgrms.kdt.domain.customer.service;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;
import org.prgrms.kdt.domain.customer.repository.CustomerRepository;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public List<Customer> getBlackCustomers() {
        List<Customer> blackCustomers = customerRepository.findByCustomerType(CustomerType.BLACK_LIST);
        logger.info("Get blackList customers size: {}", blackCustomers.size());
        return blackCustomers;
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        logger.info("Get all stored customers size: {}", customers.size());
        return customers;
    }

    @Transactional
    public Optional<Customer> getCustomerByVoucherId(UUID voucherId){
        Optional<Customer> customer = customerRepository.findByVoucherId(voucherId);
        logger.info("Get customer by voucherId: {}", customer);
        return customer;
    }

    @Transactional
    public Optional<Customer> getCustomerByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        logger.info("Get customer by email: {}", customer);
        return customer;
    }

    @Transactional
    public UUID createCustomer(String name, String email, CustomerType customerType) {
        LocalDateTime now = LocalDateTime.now();
        Customer customer = new Customer(UUID.randomUUID(), name, email, customerType, now, now);
        UUID customerId = customerRepository.save(customer);
        logger.info("create Customer {}", customer);
        return customerId;
    }

}
