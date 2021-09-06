package org.prgrms.orderApp.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    private CustomerRepository customerRepository;
    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    public List<Customer> getAllBlackLIstCustomers() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public void createCustomer(List<Customer> customers) {
        customers.forEach(customerRepository::insert);
    }
}
