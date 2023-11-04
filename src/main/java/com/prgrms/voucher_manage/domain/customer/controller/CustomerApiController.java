package com.prgrms.voucher_manage.domain.customer.controller;

import com.prgrms.voucher_manage.base.BaseResponse;
import com.prgrms.voucher_manage.domain.customer.controller.dto.CreateCustomerReq;
import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CustomerApiController {
    private final CustomerService service;

    @PostMapping("/customers")
    public BaseResponse<UUID> createCustomer(@Valid @RequestBody CreateCustomerReq request){
        Customer customer = service.save(request);
        return new BaseResponse<>(customer.getId());
    }

    @GetMapping("/customers/{customerId}")
    public BaseResponse<Customer> getCustomerById(@PathVariable UUID customerId) {
        Customer customer;
        try {
            customer = service.findById(customerId);
        } catch (Exception e){
            return new BaseResponse<>(e.getMessage());
        }
        return new BaseResponse<>(customer);
    }

    @GetMapping("/customers")
    public BaseResponse<List<Customer>> getCustomers(){
        List<Customer> customers;
        try {
            customers = service.getAllCustomers();
        } catch (Exception e){
            return new BaseResponse<>(e.getMessage());
        }
        return new BaseResponse<>(customers);
    }

    @GetMapping("/customers/black")
    public BaseResponse<List<Customer>> getBlackCustomers(){
        List<Customer> blackCustomers;
        try {
            blackCustomers = service.getBlackCustomers();
        } catch (Exception e){
            return new BaseResponse<>(e.getMessage());
        }
        return new BaseResponse<>(blackCustomers);
    }


}
