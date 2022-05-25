package org.devcourse.voucher.application.customer.controller.api;

import org.devcourse.voucher.application.customer.service.CustomerService;
import org.devcourse.voucher.application.customer.model.Customer;
import org.devcourse.voucher.application.customer.model.Email;
import org.devcourse.voucher.application.customer.service.BlacklistService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ConsoleCustomerController implements CustomerController {
    private final CustomerService customerService;
    private final BlacklistService blacklistService;

    public ConsoleCustomerController(CustomerService customerService, BlacklistService blacklistService) {
        this.customerService = customerService;
        this.blacklistService = blacklistService;
    }

    @Override
    public Customer postCreateCustomer(String name, String email) {
        return customerService.createCustomer(name, new Email(email));
    }

    @Override
    public List<Customer> getCustomerList() {
        return customerService.recallAllCustomer();
    }

    @Override
    public List<Customer> getBlackList() {
        return blacklistService.recallAllBlacklist();
    }
}
