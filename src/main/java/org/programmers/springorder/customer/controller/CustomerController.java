package org.programmers.springorder.customer.controller;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.customer.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class CustomerController {
    private final Console console;

    private final CustomerService customerService;

    public CustomerController(Console console, CustomerService customerService) {
        this.console = console;
        this.customerService = customerService;
    }

    public void printBlackList(){
        console.showBlackList(customerService.getBlackList());
    }
    
    public void getVoucherOwner(){
        UUID voucherId = console.getVoucherId();
        console.showCustomer(customerService.findOwnerOfVoucher(voucherId));
    }
}
