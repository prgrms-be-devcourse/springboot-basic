package org.prgrms.java.service;

import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.notfound.CustomerNotFoundException;
import org.prgrms.java.exception.notfound.NotFoundException;
import org.prgrms.java.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(String name, String email) {
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name(name)
                .email(email)
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();

        return customerRepository.save(customer);
    }

    public Customer getCustomer(String column, String value) {
        return switch (column) {
            case "id" -> getCustomerById(UUID.fromString(value));
            case "name" -> getCustomerByName(value);
            case "email" -> getCustomerByEmail(value);
            default -> throw new NotFoundException(column + "은(는) 찾을 수 없는 컬럼입니다.");
        };
    }

    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .filter(customer -> !customer.isBlocked())
                .orElseThrow(CustomerNotFoundException::new);
    }

    public Customer getCustomerByName(String name) {
        return customerRepository.findByName(name)
                .filter(customer -> !customer.isBlocked())
                .orElseThrow(CustomerNotFoundException::new);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .filter(customer -> !customer.isBlocked())
                .orElseThrow(CustomerNotFoundException::new);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll().stream()
                .filter(customer -> !customer.isBlocked())
                .collect(Collectors.toList());
    }

    public Customer getBlackCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .filter(Customer::isBlocked)
                .orElseThrow(CustomerNotFoundException::new);
    }

    public List<Customer> getAllBlackCustomers() {
        return customerRepository.findAll().stream()
                .filter(Customer::isBlocked)
                .collect(Collectors.toList());
    }

    public Customer updateCustomer(UUID customerId, String name, String email, boolean isBlocked) {
        Customer customer = getCustomerById(customerId);
        customer.setName(name);
        customer.setEmail(email);
        customer.setBlocked(isBlocked);

        return customerRepository.update(customer);
    }

    public void deleteCustomer(UUID customerId) {
        customerRepository.delete(customerId);
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }
}
