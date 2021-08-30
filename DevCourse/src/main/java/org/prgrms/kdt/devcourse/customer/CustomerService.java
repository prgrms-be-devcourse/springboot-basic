package org.prgrms.kdt.devcourse.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BlackCustomerRepository blackCustomerRepository;
    public CustomerService(CustomerRepository customerRepository, BlackCustomerRepository blackCustomerRepository) {
        this.customerRepository = customerRepository;
        this.blackCustomerRepository = blackCustomerRepository;
    }

    public List<Customer> getBlackCustomers(){
       return blackCustomerRepository.getBlackCustomers();
    }

}
