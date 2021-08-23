package org.prgrms.kdt.devcourse.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getBlackCustomers(){
        return customerRepository.getBlackCustomers();
    }

}
