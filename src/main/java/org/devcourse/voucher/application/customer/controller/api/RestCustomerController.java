package org.devcourse.voucher.application.customer.controller.api;

import org.devcourse.voucher.application.customer.model.Customer;
import org.devcourse.voucher.application.customer.service.CustomerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RestCustomerController implements CustomerController {

    private final CustomerService customerService;

    public RestCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Customer postCreateCustomer(String name, String email) {
        return null;
    }

    @Override
    public List<Customer> getCustomerList() {
        return null;
    }

    @Override
    public List<Customer> getBlackList() {
        return null;
    }
}
