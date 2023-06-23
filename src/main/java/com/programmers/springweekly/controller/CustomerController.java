package com.programmers.springweekly.controller;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.service.CustomerService;
import com.programmers.springweekly.view.Console;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.UUID;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final Console console;

    public CustomerController(CustomerService customerService, Console console) {
        this.customerService = customerService;
        this.console = console;
    }

    public void getCustomerBlackList(){
        Map<UUID, Customer> customerMap = customerService.getCustomerBlackList();

        if(customerMap.size() != 0){
            console.outputGetCustomerBlackList(customerMap);
            return;
        }

        console.outputErrorMessage("저장된 블랙 리스트가 없습니다.");
    }
}
