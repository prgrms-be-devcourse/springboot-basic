package com.prgrms.voucher_manage.domain.customer.controller;

import com.prgrms.voucher_manage.domain.customer.controller.dto.CreateCustomerDto;
import com.prgrms.voucher_manage.domain.customer.dto.UpdateCustomerDto;
import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.entity.CustomerType;
import com.prgrms.voucher_manage.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    public void saveCustomer(String name, CustomerType type) {
        customerService.save(new CreateCustomerDto(name, type));
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
        customerService.update(new UpdateCustomerDto(id, name));
    }

}
