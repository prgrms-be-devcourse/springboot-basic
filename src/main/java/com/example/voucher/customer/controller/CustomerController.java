package com.example.voucher.customer.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.ResponseStatus;
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
        try {
            CustomerDTO customer = customerService.createCustomer(request.getName(), request.getEmail(),
                request.getCustomerType());

            return new CustomerResponse(ResponseStatus.SC, customer);
        } catch (Exception e) {
            return new CustomerResponse(ResponseStatus.ER, e.getMessage());
        }
    }

    public CustomerResponse getCustomers() {
        try {
            List<CustomerDTO> customers = customerService.getCustomers();

            return new CustomerResponse(ResponseStatus.SC, customers);
        } catch (Exception e) {
            return new CustomerResponse(ResponseStatus.ER, e.getMessage());
        }
    }

    public CustomerResponse getCustomer(CustomerRequest request) {
        try {
            CustomerDTO customer = customerService.getCustomer(request.getCustomerId());

            return new CustomerResponse(ResponseStatus.SC, customer);
        } catch (Exception e) {
            return new CustomerResponse(ResponseStatus.ER, e.getMessage());
        }
    }

    public CustomerResponse update(CustomerRequest request) {
        try {
            CustomerDTO customer = customerService.update(request.getCustomerId(), request.getName(),
                request.getEmail(),
                request.getCustomerType());

            return new CustomerResponse(ResponseStatus.SC, customer);
        } catch (Exception e) {
            return new CustomerResponse(ResponseStatus.ER, e.getMessage());
        }
    }

    public CustomerResponse deleteCustomers() {
        try {
            customerService.deleteCustomers();

            return new CustomerResponse(ResponseStatus.SC);
        } catch (Exception e) {
            return new CustomerResponse(ResponseStatus.ER, e.getMessage());
        }
    }

    public CustomerResponse deleteCustomer(CustomerRequest request) {
        try {
            customerService.deleteCustomer(request.getCustomerId());

            return new CustomerResponse(ResponseStatus.SC);
        } catch (Exception e) {
            return new CustomerResponse(ResponseStatus.ER, e.getMessage());
        }
    }

}
