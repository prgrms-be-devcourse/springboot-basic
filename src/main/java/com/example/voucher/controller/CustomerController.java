package com.example.voucher.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import com.example.voucher.controller.request.CustomerRequest;
import com.example.voucher.controller.response.Response;
import com.example.voucher.service.customer.CustomerService;
import com.example.voucher.service.customer.dto.CustomerDTO;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    private CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Response<CustomerDTO> createCustomer(CustomerRequest.Create request) {
        CustomerDTO customer = customerService.createCustomer(request.getName(), request.getEmail(),
            request.getCustomerType());

        return new Response<>(customer);
    }

    public Response<CustomerDTO> getCustomers() {
        List<CustomerDTO> customers = customerService.getCustomers();
        return new Response<>(customers);
    }

    public void deleteCustomers() {
        customerService.deleteCustomers();
    }

    public Response<CustomerDTO> search(UUID customerId) {
        CustomerDTO customer = customerService.search(customerId);
        return new Response<>(customer);
    }

    public Response<CustomerDTO> update(CustomerRequest.Update request) {
        CustomerDTO customer = customerService.update(request.getCustomerId(), request.getName(), request.getEmail(),
            request.getCustomerType());
        return new Response<>(customer);
    }

    public void deleteCustomer(UUID customerId) {
        customerService.deleteCustomer(customerId);
    }

}
