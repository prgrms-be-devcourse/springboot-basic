package org.devcourse.voucher.application.customer.controller;

import org.devcourse.voucher.application.customer.controller.dto.CreateCustomerRequest;
import org.devcourse.voucher.application.customer.model.Customer;
import org.devcourse.voucher.application.customer.model.Email;
import org.devcourse.voucher.application.customer.service.CustomerService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class WebCustomerController {

    private final CustomerService customerService;

    public WebCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public String customerMainPage(Model model, Pageable pageable) {
        List<Customer> customers = customerService.recallAllCustomer(pageable).getContent();
        model.addAttribute("customers", customers);
        return "customer/index";
    }

    @GetMapping("/new")
    public String customerCreatePage() {
        return "customer/new";
    }

    @PostMapping("/new")
    public String postCreateCustomer(CreateCustomerRequest createCustomerRequest) {
        customerService.createCustomer(createCustomerRequest.name(), new Email(createCustomerRequest.email()));
        return "redirect:";
    }
}
