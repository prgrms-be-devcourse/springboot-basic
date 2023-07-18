package com.devcourse.voucherapp.controller.web;

import com.devcourse.voucherapp.entity.customer.dto.CustomerCreateRequestDto;
import com.devcourse.voucherapp.entity.customer.dto.CustomerResponseDto;
import com.devcourse.voucherapp.service.CustomerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
@Profile("dev")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/create")
    public String getCreateForm() {
        return "customer/createForm";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute CustomerCreateRequestDto request) {
        customerService.create(request);

        return "redirect:/customers";
    }

    @GetMapping
    public String findAllCustomers(Model model) {
        List<CustomerResponseDto> customers = customerService.findAllCustomers();
        model.addAttribute("customers", customers);

        return "customer/customers";
    }
}
