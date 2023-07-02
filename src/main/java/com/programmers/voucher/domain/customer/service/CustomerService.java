package com.programmers.voucher.domain.customer.service;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.repository.BlacklistRepository;
import com.programmers.voucher.domain.customer.repository.CustomerRepository;
import com.programmers.voucher.domain.customer.util.CustomerMessages;
import com.programmers.voucher.global.util.DataErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    private final BlacklistRepository blacklistRepository;
    private final CustomerRepository customerRepository;

    public CustomerService(BlacklistRepository blacklistRepository, CustomerRepository customerRepository) {
        this.blacklistRepository = blacklistRepository;
        this.customerRepository = customerRepository;
    }

    public List<Customer> findBlacklistCustomers() {
        return blacklistRepository.findAll();
    }

    public UUID save(String email, String name) {
        boolean emailDuplication = customerRepository.findByEmail(email)
                .isPresent();
        if(emailDuplication) {
            String errorMessage = String.format(DataErrorMessages.DUPLICATE_EMAIL, email);
            throw new DuplicateKeyException(errorMessage);
        }

        Customer customer = new Customer(UUID.randomUUID(), email, name);
        customerRepository.save(customer);
        LOG.info(CustomerMessages.CREATED_NEW_CUSTOMER, customer);

        return customer.getCustomerId();
    }

    public void update(UUID customerId, String name) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException(DataErrorMessages.NO_SUCH_ELEMENT));
        String oldCustomerInfo = customer.toString();

        customer.changeName(name);
        customerRepository.update(customer);
        String newCustomerInfo = customer.toString();

        LOG.info(CustomerMessages.UPDATED_CUSTOMER_INFO, oldCustomerInfo, newCustomerInfo);
    }

    public List<Customer> findCustomers() {
        return customerRepository.findAll();
    }
}
