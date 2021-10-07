package org.prgrms.orderApp.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomersList() {
        return customerRepository.findAll();
    }

    public void createCustomer(List<Customer> customers) {
        customers.forEach(customerRepository::insert);
    }
}
