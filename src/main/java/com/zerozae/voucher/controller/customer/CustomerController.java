package com.zerozae.voucher.controller.customer;

import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.service.customer.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Response createCustomer(CustomerRequest customerRequest){
        try{
            customerService.createCustomer(customerRequest);
        }catch (ErrorMessage e){
            return Response.failure(e.getMessage());
        }
        return Response.success();
    }

    public Response findAllCustomers(){
        List<CustomerResponse> customers = customerService.findAllCustomers();
        return Response.success(customers);
    }

    public Response findAllBlacklistCustomers(){
        List<CustomerResponse> blacklistCustomer = customerService.findAllBlacklistCustomer();
        return Response.success(blacklistCustomer);
    }
}
