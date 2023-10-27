package com.prgrms.voucher_manage.domain.customer.controller;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    public Customer save(Customer customer){
        return customerService.save(customer);
    }
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    public List<Customer> getBlackCustomers() {
        return customerService.getBlackCustomers();
    }
    public List<Customer> getNormalCustomers(){
        return customerService.getNormalCustomers();
    }
    public Customer findById(UUID id){
        return customerService.findById(id);
    }
    public Customer findByName(String name){
        return customerService.findByName(name);
    }
    public void update(Customer customer){
        customerService.update(customer);
    }

}
