package org.prgrms.orderApp.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    public List<Customer> getAllBlackLIstCustomers() {
        return customerRepository.findAll();
    }
    //@Transactional
    public void createCustomer(List<Customer> customers) {
        customers.forEach(customerRepository::insert);
    }
}
