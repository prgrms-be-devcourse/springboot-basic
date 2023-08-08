package com.prgms.voucher.voucherproject.service;

import com.prgms.voucher.voucherproject.domain.customer.Customer;
import com.prgms.voucher.voucherproject.repository.customer.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        try {
            customerRepository.save(customer);
        } catch (DuplicateKeyException e) {
            logger.error("customer DuplicateKeyException email -> {}", customer.getEmail());
        }
    }

    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public void deleteByEmail(String email) {
        customerRepository.deleteByEmail(email);
    }

}
