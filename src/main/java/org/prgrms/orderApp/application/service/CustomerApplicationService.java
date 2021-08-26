package org.prgrms.orderApp.application.service;


import org.prgrms.orderApp.domain.customer.model.Customer;
import org.prgrms.orderApp.domain.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerApplicationService {
    @Autowired
    CustomerService customerService;

    public List<Customer> getAllBlackList(){
        return customerService.getAllBlackLIstCustomers();
    }
}
