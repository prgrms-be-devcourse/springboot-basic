package com.example.springbootbasic.controller.customer;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.dto.customer.CustomerDto;
import com.example.springbootbasic.service.customer.JdbcCustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/view")
public class ViewCustomerController {

    private final JdbcCustomerService customerService;

    public ViewCustomerController(JdbcCustomerService jdbcCustomerService) {
        this.customerService = jdbcCustomerService;
    }

    @GetMapping("/v1/customers")
    public String customerList(Model model) {
        List<Customer> findAllCustomers = customerService.findAllCustomers();
//        findAllCustomers.forEach(customer ->
//                customerService.findVouchersByCustomerId(customer.getCustomerId())
//                        .forEach(customer::receiveFrom));
//
        List<CustomerDto> result = findAllCustomers.stream()
                .map(CustomerDto::newInstance)
                .toList();
        model.addAttribute("customers", result);
        return "customer-list";
    }
}
