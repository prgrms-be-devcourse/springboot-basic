package com.example.springbootbasic.controller.customer;

import com.example.springbootbasic.controller.response.ResponseBody;
import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.dto.customer.CustomerDto;
import com.example.springbootbasic.service.customer.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;

@Controller
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public ResponseBody<List<CustomerDto>> selectAllBlackCustomers() {
        List<Customer> findAllBlackCustomers;
        try {
            findAllBlackCustomers = customerService.findAllCustomers();
        } catch (NullPointerException e) {
            logger.error("Fail - {}", e.getMessage());
            return ResponseBody.fail(Collections.emptyList());
        }
        return ResponseBody.success(findAllBlackCustomers.stream()
                .map(CustomerDto::toDto)
                .toList());
    }
}
