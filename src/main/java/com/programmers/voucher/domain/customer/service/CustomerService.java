package com.programmers.voucher.domain.customer.service;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.repository.CustomerRepository;
import com.programmers.voucher.domain.customer.util.CustomerMessages;
import com.programmers.voucher.global.util.DataErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<Customer> findBlacklistCustomers() {
        return customerRepository.findAllByBanned();
    }

    @Transactional
    public UUID createCustomer(String email, String name) {
        boolean emailDuplication = customerRepository.findByEmail(email)
                .isPresent();
        if (emailDuplication) {
            String errorMessage = String.format(DataErrorMessages.DUPLICATE_EMAIL, email);
            throw new DuplicateKeyException(errorMessage);
        }

        Customer customer = new Customer(UUID.randomUUID(), email, name);
        customerRepository.save(customer);
        LOG.info(CustomerMessages.CREATED_NEW_CUSTOMER, customer);

        return customer.getCustomerId();
    }

    @Transactional
    public void updateCustomer(UUID customerId, String name, boolean banned) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException(DataErrorMessages.NO_SUCH_ELEMENT));
        String oldCustomerInfo = customer.toString();

        customer.update(name, banned);

        customerRepository.update(customer);
        String newCustomerInfo = customer.toString();

        LOG.info(CustomerMessages.UPDATED_CUSTOMER_INFO, oldCustomerInfo, newCustomerInfo);
    }

    @Transactional(readOnly = true)
    public List<Customer> findCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public void deleteCustomer(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException(DataErrorMessages.NO_SUCH_ELEMENT));

        customerRepository.deleteById(customerId);
        LOG.info(CustomerMessages.DELETED_CUSTOMER, customer);
    }
}
