package com.zerozae.voucher.controller.customer;

import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.service.customer.CustomerService;
import org.springframework.stereotype.Controller;

import static com.zerozae.voucher.common.message.MessageConverter.getMessage;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Response createCustomer(CustomerRequest customerRequest){
        try{
            validateCustomerInfo(customerRequest);
            customerService.createCustomer(customerRequest);
        }catch (ErrorMessage e){
            return Response.failure(e.getMessage());
        }
        return Response.success();
    }

    public Response findAllCustomers(){
        return Response.success(customerService.findAllCustomers().stream().map(CustomerResponse::getInfo).toList());
    }

    public Response findAllBlacklistCustomers(){
        return Response.success(customerService.findAllBlacklistCustomer().stream().map(CustomerResponse::getInfo).toList());
    }

    private void validateCustomerInfo(CustomerRequest customerRequest) {
        if(customerRequest.getCustomerName().isBlank()){
            throw ErrorMessage.error(getMessage("EMPTY_CUSTOMER_NAME.MSG"));
        }
    }
}
