package com.example.voucher.customer.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.voucher.constant.CustomerType;
import com.example.voucher.customer.model.Customer;
import com.example.voucher.customer.repository.CustomerRepository;
import com.example.voucher.customer.service.dto.CustomerDTO;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO createCustomer(String name, String email, CustomerType customerType) {
        Customer createdCustomer = Customer.builder()
            .name(name)
            .email(email)
            .customerType(customerType)
            .build();

        Customer savedCustomer = customerRepository.save(createdCustomer);

        return toDTO(savedCustomer);
    }

    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
            .stream()
            .map(v -> toDTO(v))
            .toList();
    }

    public void deleteCustomers() {
        customerRepository.deleteAll();
    }

    public CustomerDTO getCustomer(UUID customerId) {
        Customer selectedCustomer = customerRepository.findById(customerId);

        return toDTO(selectedCustomer);
    }

    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    public CustomerDTO update(UUID customerId, String customerName, String customerEmail, CustomerType customerType) {
        Customer customer = Customer.builder()
            .customerId(customerId)
            .name(customerName)
            .email(customerEmail)
            .customerType(customerType)
            .build();

        Customer updatedCustomer = customerRepository.update(customer);

        return toDTO(updatedCustomer);
    }

    private CustomerDTO toDTO(Customer customer) {
        UUID customerId = customer.getCustomerId();
        String name = customer.getName();
        String email = customer.getEmail();
        CustomerType customerType = customer.getCustomerType();
        LocalDateTime createdAt = customer.getCreatedAt();

        return new CustomerDTO(customerId, name, email, customerType, createdAt);
    }

}
