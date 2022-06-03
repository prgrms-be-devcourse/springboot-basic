package org.devcourse.voucher.application.customer.controller.api;

import org.devcourse.voucher.application.customer.model.Customer;
import org.devcourse.voucher.application.customer.service.CustomerService;
import org.devcourse.voucher.core.utils.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class RestCustomerController {

    private final CustomerService customerService;

    public RestCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(name = "")
    public ApiResponse<Customer> postCreateCustomer(String name, String email) {
        return null;
    }

    @GetMapping(name = "")
    public ApiResponse<List<Customer>> getCustomerList(Pageable pageable) {
        return null;
    }

}
