package com.prgrms.kdt.springbootbasic.service;

import com.prgrms.kdt.springbootbasic.entity.Customer;
import com.prgrms.kdt.springbootbasic.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(String name, String email){
        return new Customer(UUID.randomUUID(), name, email);
    }

    public Optional<Customer> saveCustomer(Customer customer){
        if (checkDuplication(customer)){
            return Optional.empty();
        }
        return customerRepository.saveCustomer(customer);
    }

    public boolean checkDuplication(Customer customer){
        var findResult = customerRepository.findCustomerByEmail(customer.getEmail());
        if(findResult.isEmpty())
            return false;
        return true;
    }

    public Optional<Customer> findCustomerById(UUID customerId){
        return customerRepository.findCustomerById(customerId);
    }

    public List<Customer> getAllCustomers(){return customerRepository.getAllCustomers();}

}
