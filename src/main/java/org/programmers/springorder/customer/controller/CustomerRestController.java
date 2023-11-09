package org.programmers.springorder.customer.controller;

import org.programmers.springorder.customer.dto.CustomerRequestDto;
import org.programmers.springorder.customer.dto.CustomerResponseDto;
import org.programmers.springorder.customer.service.CustomerService;
import org.programmers.springorder.voucher.dto.Response;
import org.springframework.context.annotation.Profile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Profile("default")
@RestController
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("api/v1/customers")
    public Response<List<CustomerResponseDto>> getCustomerListPage(){
        return Response.success(customerService.getAllCustomers());
    }

    @GetMapping("api/v1/blackCustomers")
    public Response<List<CustomerResponseDto>> getBlackCustomerListPage(){
        return Response.success(customerService.getBlackList());
    }

    @GetMapping("api/v1/customers/{customerId}")
    public Response<CustomerResponseDto> getCustomerDetail(@PathVariable UUID customerId, Model model){
        return Response.success(customerService.findCustomer(customerId));
    }


    @PostMapping("api/v1/customers")
    public Response<Void> enrollCustomer(CustomerRequestDto requestDto){
        customerService.newCustomer(requestDto);
        return Response.success();
    }

}
