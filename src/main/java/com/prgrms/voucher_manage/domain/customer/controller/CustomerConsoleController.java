package com.prgrms.voucher_manage.domain.customer.controller;

import com.prgrms.voucher_manage.domain.customer.controller.dto.CreateCustomerReq;
import com.prgrms.voucher_manage.domain.customer.controller.dto.UpdateCustomerReq;
import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.entity.CustomerType;
import com.prgrms.voucher_manage.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CustomerConsoleController {
    private final CustomerService customerService;

    public void saveCustomer(String name, CustomerType type) {
        customerService.save(new CreateCustomerReq(name, type));
    }

    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public List<Customer> getBlackCustomers() {
        return customerService.getBlackCustomers();
    }

    public Customer findById(UUID id) {
        return customerService.findById(id);
    }

    public Customer findByName(String name) {
        return customerService.findByName(name);
    }

    public void update(UUID id, String name) {
        customerService.update(id, new UpdateCustomerReq(name));
    }

}
