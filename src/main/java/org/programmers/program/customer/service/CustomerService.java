package org.programmers.program.customer.service;

import org.programmers.program.customer.controller.CustomerDto;
import org.programmers.program.customer.model.Customer;
import org.programmers.program.customer.repository.CustomerRepository;
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

    public Customer createCustomer(CustomerDto dto){
        if (validate(dto.to()))
            return customerRepository.insert(dto.to());
        return null;
    }

    public Optional<Customer> findById(UUID id){
        return customerRepository.findById(id);
    }

    public Optional<Customer> findByName(String name){
        return customerRepository.findByName(name);
    }

    public Optional<Customer> findByEmail(String email){
        return customerRepository.findByEmail(email);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public int updateUser(Customer customer){
        return customerRepository.update(customer);
    }

    public void clearCustomerRepository(){
        customerRepository.deleteAll();
    }

    public int count(){
        return this.customerRepository.count();
    }

    private boolean validate(Customer customer){
        if(findById(customer.getId()).isPresent() || findByEmail(customer.getEmail()).isPresent() ||
                findByName(customer.getName()).isPresent())
            return false;
        return true;
    }
}
