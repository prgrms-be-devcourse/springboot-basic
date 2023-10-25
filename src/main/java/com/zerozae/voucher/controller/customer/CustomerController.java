package com.zerozae.voucher.controller.customer;

import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.exception.ErrorMessage;
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

    public Response createCustomer(CustomerRequest customerRequest){
        try{
            customerService.createCustomer(customerRequest);
            return Response.success();
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public Response findAllCustomers(){
        List<CustomerResponse> customers = customerService.findAllCustomers();
        return Response.success(customers);
    }

    public Response findAllBlacklistCustomers(){
        List<CustomerResponse> blacklistCustomer = customerService.findAllBlacklistCustomers();
        return Response.success(blacklistCustomer);
    }

    public Response findCustomerById(UUID customerId) {
        try {
            CustomerResponse customerReponse = customerService.findById(customerId);
            return Response.success(customerReponse);
        }catch (Exception e) {
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public Response deleteCustomerById(UUID customerId) {
        try {
            customerService.deleteById(customerId);
            return Response.success();
        }catch (Exception e) {
            throw ErrorMessage.error(e.getMessage());
        }
    }
    public Response deleteAllCustomers() {
        customerService.deleteAll();
        return Response.success();
    }

    public Response updateCustomer(UUID customerId, CustomerRequest customerRequest) {
        try {
            customerService.update(customerId, customerRequest);
            return Response.success();
        }catch (Exception e) {
            throw ErrorMessage.error(e.getMessage());
        }
    }
}
