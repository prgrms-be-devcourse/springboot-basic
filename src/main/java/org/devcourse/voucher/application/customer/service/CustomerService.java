package org.devcourse.voucher.application.customer.service;

import org.devcourse.voucher.application.customer.controller.dto.CustomerRequest;
import org.devcourse.voucher.application.customer.controller.dto.CustomerResponse;
import org.devcourse.voucher.application.customer.model.Customer;
import org.devcourse.voucher.application.customer.model.Email;
import org.devcourse.voucher.application.customer.repository.CustomerRepository;
import org.devcourse.voucher.core.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.devcourse.voucher.core.exception.ErrorType.NOT_FOUND_CUSTOMER;

@Transactional
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse createCustomer(String name, Email email) {
        Customer customer = new Customer(UUID.randomUUID(), name, email);
        return CustomerResponse.of(customerRepository.insert(customer));
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> recallAllCustomer(Pageable pageable) {
        return customerRepository
                .findAll(pageable)
                .stream()
                .map(CustomerResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponse recallCustomerById(UUID customerId) {
        return customerRepository
                .findById(customerId)
                .map(CustomerResponse::of)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CUSTOMER, customerId));
    }

    public CustomerResponse updateCustomer(UUID customerId, String name, Email email) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CUSTOMER, customerId));
        customer.setName(name);
        customer.setEmail(email);
        return CustomerResponse.of(customerRepository.update(customer));
    }

    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }
}
