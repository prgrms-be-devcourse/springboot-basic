package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.repository.CustomerRepository;
import org.prgrms.kdt.service.dto.CreateCustomerDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean createCustomer(CreateCustomerDto createCustomerDto) {
        Customer newCustomer = new Customer(createCustomerDto.email());
        Optional<Customer> returnedCustomer = customerRepository.saveCustomer(newCustomer);
        return returnedCustomer.isPresent();
    }

    public Optional<Customer> getCustomerByEmail(String email){
        return customerRepository.getCustomerByEmail(email);
    }

    public Optional<Customer> getCustomerById(long customerId) {
        return customerRepository.getCustomerById(customerId);
    }

    public boolean hasDuplicatedCustomer(String email){
        return getCustomerByEmail(email).isPresent();
    }
}
