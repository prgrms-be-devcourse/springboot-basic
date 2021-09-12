package org.prgrms.kdt.controller.customer;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.service.SimpleCustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class CustomerController {

    private final SimpleCustomerService customerService;

    public CustomerController(SimpleCustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/api/v1/customers")
    @ResponseBody
    public List<Customer> findCustomers() {
        return customerService.getAllCustomers();
    }
}
