package org.voucherProject.voucherProject.customer.controller;

import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.customer.entity.CustomerDto;

import java.util.List;

public interface CustomerController {

    Customer createCustomer(CustomerDto customerDto);

    List<CustomerDto> findAll();

    CustomerDto findById(CustomerDto customerDto);

    CustomerDto findByName(CustomerDto customerDto);

    CustomerDto findByEmail(CustomerDto customerDto);

}
