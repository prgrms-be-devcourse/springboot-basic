package com.example.voucher.customer.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import com.example.voucher.customer.service.CustomerService;
import com.example.voucher.customer.service.dto.CustomerDTO;
import com.example.voucher.response.Response;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    private CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Response<CustomerDTO> createCustomer(CustomerRequest request) {
        CustomerDTO customer = customerService.createCustomer(request.getName(), request.getEmail(),
            request.getCustomerType());

        return new Response<>(customer);
    }

    public Response<CustomerDTO> getCustomers() {
        List<CustomerDTO> customers = customerService.getCustomers();

        return new Response<>(customers);
    }

    public Response<CustomerDTO> getCustomer(CustomerRequest request) {
        CustomerDTO customer = customerService.search(request.getCustomerId());

        return new Response<>(customer);
    }

    public Response<CustomerDTO> update(CustomerRequest request) {
        CustomerDTO customer = customerService.update(request.getCustomerId(), request.getName(), request.getEmail(),
            request.getCustomerType());

        return new Response<>(customer);
    }

    public void deleteCustomers() {
        customerService.deleteCustomers();
    }

    public void deleteCustomer(CustomerRequest request) {
        customerService.deleteCustomer(request.getCustomerId());
    }

}
