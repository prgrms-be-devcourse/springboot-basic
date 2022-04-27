package com.kdt.commandLineApp.console;

import com.kdt.commandLineApp.service.customer.CustomerService;
import com.kdt.commandLineApp.io.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMainLogic {
    private IO io;
    private CustomerService customerService;

    @Autowired
    public CustomerMainLogic(IO io, CustomerService customerService) {
        this.io = io;
        this.customerService = customerService;
    }

    public void showBlackList() throws Exception {
        io.print(customerService.getCustomers());
    }
}
