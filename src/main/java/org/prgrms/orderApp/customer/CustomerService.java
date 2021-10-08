package org.prgrms.orderApp.customer;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomersList() {
        return customerRepository.findAll();
    }
    //@Transactional
    public void createCustomer(List<Customer> customers) {
        customers.forEach(customerRepository::insert);
    }
}
