package com.programmers.voucher.domain.customer.service;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.dto.CustomerDto;
import com.programmers.voucher.domain.customer.repository.CustomerRepository;
import com.programmers.voucher.domain.customer.util.CustomerErrorMessages;
import com.programmers.voucher.domain.customer.util.CustomerMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
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
    public List<CustomerDto> findBlacklistCustomers() {
        List<Customer> customers = customerRepository.findAllByBanned();
        return customers.stream()
                .map(CustomerDto::from)
                .toList();
    }

    @Transactional
    public UUID createCustomer(String email, String name) {
        customerRepository.findByEmail(email)
                .ifPresent(customer -> {
                    String errorMessage = MessageFormat.format(CustomerErrorMessages.DUPLICATE_EMAIL, email);
                    LOG.warn(errorMessage);
                    throw new DuplicateKeyException(errorMessage);
                });

        Customer customer = new Customer(UUID.randomUUID(), email, name);
        customerRepository.save(customer);
        LOG.info(CustomerMessages.CREATED_NEW_CUSTOMER, customer);

        return customer.getCustomerId();
    }

    @Transactional(readOnly = true)
    public List<CustomerDto> findCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerDto findCustomer(UUID customerId) {
        Customer customer = findCustomerOrElseThrow(customerId);

        return CustomerDto.from(customer);
    }

    @Transactional
    public void updateCustomer(UUID customerId, String name, boolean banned) {
        Customer customer = findCustomerOrElseThrow(customerId);
        String oldCustomerInfo = customer.toString();

        customer.update(name, banned);
        customerRepository.update(customer);
        String newCustomerInfo = customer.toString();

        LOG.info(CustomerMessages.UPDATED_CUSTOMER_INFO, oldCustomerInfo, newCustomerInfo);
    }

    @Transactional
    public void deleteCustomer(UUID customerId) {
        Customer customer = findCustomerOrElseThrow(customerId);

        customerRepository.deleteById(customerId);
        LOG.info(CustomerMessages.DELETED_CUSTOMER, customer);
    }

    private Customer findCustomerOrElseThrow(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    String errorMessage = MessageFormat.format(CustomerErrorMessages.NO_SUCH_CUSTOMER, customerId);
                    LOG.warn(errorMessage);
                    return new NoSuchElementException(errorMessage);
                });
        return customer;
    }
}
