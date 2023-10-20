package com.prgms.vouchermanager.contorller.customer;

import com.prgms.vouchermanager.domain.customer.Customer;
import com.prgms.vouchermanager.service.customer.CustomerService;
import com.prgms.vouchermanager.util.io.ConsoleInput;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.prgms.vouchermanager.contorller.customer.CustomerMenuType.*;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    public void getBlackList() {
        List<Customer> blackList = customerService.getBlackList();

        blackList.
                forEach(customer -> System.out.println(customer.toString()));
    }
}
