package com.prgrms.kdt.springbootbasic.service;

import com.prgrms.kdt.springbootbasic.entity.Customer;
import com.prgrms.kdt.springbootbasic.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
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

    public Optional<Customer> updateCustomer(Customer customer){
        Optional<Customer> foundCustomer = findCustomerById(customer.getCustomerId());
        if (foundCustomer.isEmpty())
            return Optional.empty();

        //수정될 내용이 없으면 empty return
        if (customer.getName().equals(foundCustomer.get().getName()))
            return Optional.empty();

        return customerRepository.updateCustomer(customer);
    }

    public boolean deleteCustomer(Customer customer){
        Optional<Customer> foundCustomer = findCustomerById(customer.getCustomerId());
        if (foundCustomer.isEmpty())
            return false;

        return customerRepository.deleteCustomer(customer);
    }
}
