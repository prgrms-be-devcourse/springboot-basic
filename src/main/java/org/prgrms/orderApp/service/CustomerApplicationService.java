package org.prgrms.orderApp.service;


import org.prgrms.orderApp.domain.customer.model.Customer;
import org.prgrms.orderApp.domain.customer.service.CustomerService;
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
