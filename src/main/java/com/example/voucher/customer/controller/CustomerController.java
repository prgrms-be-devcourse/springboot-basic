package com.example.voucher.customer.controller;

import java.util.List;
import org.springframework.stereotype.Controller;

import com.example.voucher.customer.controller.model.CustomerRequest;
import com.example.voucher.customer.controller.model.CustomerResponse;
import com.example.voucher.customer.service.CustomerService;
import com.example.voucher.customer.service.dto.CustomerDTO;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    private CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        CustomerDTO customer = customerService.createCustomer(request.getName(), request.getEmail(),
            request.getCustomerType());

        return new CustomerResponse(customer);
    }

    public CustomerResponse getCustomers() {
        List<CustomerDTO> customers = customerService.getCustomers();

        return new CustomerResponse(customers);
    }

    public CustomerResponse getCustomer(CustomerRequest request) {
        CustomerDTO customer = customerService.getCustomer(request.getCustomerId());

        return new CustomerResponse(customer);
    }

    public CustomerResponse update(CustomerRequest request) {
        CustomerDTO customer = customerService.update(request.getCustomerId(), request.getName(), request.getEmail(),
            request.getCustomerType());

        return new CustomerResponse(customer);
    }

    public void deleteCustomers() {
        customerService.deleteCustomers();
    }

    public void deleteCustomer(CustomerRequest request) {
        customerService.deleteCustomer(request.getCustomerId());
    }

}
