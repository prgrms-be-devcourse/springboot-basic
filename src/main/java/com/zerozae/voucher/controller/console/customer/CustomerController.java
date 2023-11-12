package com.zerozae.voucher.controller.console.customer;

import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.dto.customer.CustomerCreateRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.dto.customer.CustomerUpdateRequest;
import com.zerozae.voucher.exception.ExceptionMessage;
import com.zerozae.voucher.service.customer.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;


@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Response createCustomer(CustomerCreateRequest customerRequest) {
        try{
            customerService.createCustomer(customerRequest);
            return Response.success();
        }catch (ExceptionMessage e){
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public Response<List<CustomerResponse>> findAllCustomers() {
        List<CustomerResponse> customers = customerService.findAllCustomers();
        return Response.success(customers);
    }

    public Response<List<CustomerResponse>> findAllBlacklistCustomers() {
        List<CustomerResponse> blacklistCustomer = customerService.findAllBlacklistCustomers();
        return Response.success(blacklistCustomer);
    }

    public Response<CustomerResponse> findCustomerById(UUID customerId) {
        try {
            CustomerResponse customerResponse = customerService.findById(customerId);
            return Response.success(customerResponse);
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public Response deleteCustomerById(UUID customerId) {
        try {
            customerService.deleteById(customerId);
            return Response.success();
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }
    public Response deleteAllCustomers() {
        customerService.deleteAll();
        return Response.success();
    }

    public Response updateCustomer(UUID customerId, CustomerUpdateRequest customerRequest) {
        try {
            customerService.update(customerId, customerRequest);
            return Response.success();
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }
}
