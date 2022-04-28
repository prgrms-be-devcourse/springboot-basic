package org.prgrms.springbootbasic.controller.web;

import org.prgrms.springbootbasic.service.CustomerService;
import org.prgrms.springbootbasic.util.DtoConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
public class WebCustomerController {

    private final CustomerService customerService;

    public WebCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String viewAllVouchers(Model model) {
        var customers = customerService.findAllCustomers();
        model.addAttribute("customerDtos", DtoConverter.toCustomerDtos(customers));
        return "customer/customers";
    }
}
