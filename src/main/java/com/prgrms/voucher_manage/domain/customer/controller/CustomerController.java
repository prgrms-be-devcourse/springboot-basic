package com.prgrms.voucher_manage.domain.customer.controller;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    public List<Customer> getBlackCustomers() {
        return customerService.getBlackCustomers();
    }
    public List<Customer> getNormalCustomers(){
        return customerService.getNormalCustomers();
    }
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    public Customer findByName(String name){
        return customerService.findByName(name);
    }
    public void update(Customer customer){
        customerService.update(customer);
    }
    public Customer save(Customer customer){
        return customerService.save(customer);
    }
}
