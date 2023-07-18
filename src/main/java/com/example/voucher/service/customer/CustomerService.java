package com.example.voucher.service.customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.voucher.constant.CustomerType;
import com.example.voucher.domain.customer.Customer;
import com.example.voucher.repository.customer.CustomerRepository;
import com.example.voucher.service.customer.dto.CustomerDTO;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO createCustomer(String name, String email, CustomerType customerType) {
        Customer createdCustomer = new Customer(name, email, customerType);
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

    public CustomerDTO search(UUID customerId) {
        Customer selectedCustomer = customerRepository.findById(customerId);

        return toDTO(selectedCustomer);
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
