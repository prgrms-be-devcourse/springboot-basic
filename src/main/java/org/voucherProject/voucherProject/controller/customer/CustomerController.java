package org.voucherProject.voucherProject.controller.customer;

import org.voucherProject.voucherProject.entity.customer.Customer;
import org.voucherProject.voucherProject.entity.customer.CustomerDto;

import java.util.List;

public interface CustomerController {

    Customer createCustomer(CustomerDto customerDto);

    List<CustomerDto> findAll();

    CustomerDto findById(CustomerDto customerDto);

}
