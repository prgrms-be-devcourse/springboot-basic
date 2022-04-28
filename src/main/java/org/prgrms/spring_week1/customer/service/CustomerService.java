package org.prgrms.spring_week1.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.spring_week1.customer.model.Customer;
import org.prgrms.spring_week1.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(
        CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> findById(UUID customerId){
        return customerRepository.findById(customerId);
    }

    public List<Customer> getAllCustomer(){
        return customerRepository.getAll();
    }

    public Customer update(Customer customer){
        return customerRepository.update(customer);
    }

    public Customer insert(Customer customer){
        return customerRepository.insert(customer);
    }

    public void deleteAll(){
        customerRepository.deleteAll();
    }
}
