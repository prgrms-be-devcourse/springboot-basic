package org.prgrms.orderApp.commandOperator.service;

import org.prgrms.orderApp.customer.Customer;
import org.prgrms.orderApp.customer.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerApplicationService {
    private CustomerService customerService;
    public CustomerApplicationService(CustomerService customerService){
        this.customerService = customerService;
    }
    public List<Customer> getAllBlackList(){
        return customerService.getAllBlackLIstCustomers();
    }
}
