package com.programmers.springweekly.controller;

import com.programmers.springweekly.dto.customer.request.CustomerCreateRequest;
import com.programmers.springweekly.dto.customer.response.CustomerListResponse;
import com.programmers.springweekly.dto.customer.response.CustomerResponse;
import com.programmers.springweekly.service.CustomerService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/customer")
public class CustomerViewController {

    private final CustomerService customerService;

    @GetMapping
    public String getMenuPage() {
        return "customer/menu";
    }

    @GetMapping("/save")
    public String getCreatePage() {
        return "customer/create";
    }

    @PostMapping("/save")
    public String save(@Valid CustomerCreateRequest customerCreateRequest) {
        customerService.save(customerCreateRequest);

        return "customer/menu";
    }

    @GetMapping("/findAll")
    public String getFindAllPage(Model model) {
        CustomerListResponse customerListResponse = customerService.findAll();
        model.addAttribute("customerList", customerListResponse.getCustomerList());

        return "customer/findAll";
    }

    @GetMapping("/find/{id}")
    public String findById(@PathVariable("id") UUID customerId, Model model) {
        CustomerResponse customerResponse = customerService.findById(customerId);
        model.addAttribute("customer", customerResponse);

        return "customer/find";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") UUID customerId) {
        customerService.deleteById(customerId);

        return "redirect:/view/customer/find";
    }

}
