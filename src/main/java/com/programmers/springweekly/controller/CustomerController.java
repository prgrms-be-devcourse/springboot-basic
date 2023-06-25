package com.programmers.springweekly.controller;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.service.CustomerService;
import com.programmers.springweekly.view.Console;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final Console console;

    public void getBlackList(){
        Map<UUID, Customer> customerMap = customerService.getBlackList();

        if(customerMap.size() != 0){
            console.outputGetCustomerBlackList(customerMap);
            return;
        }

        console.outputErrorMessage("There are no saved blacklists.");
    }
}
