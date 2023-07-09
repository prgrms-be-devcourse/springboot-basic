package org.promgrammers.springbootbasic.domain.customer.controller;

import lombok.RequiredArgsConstructor;
import org.promgrammers.springbootbasic.domain.customer.dto.request.CreateCustomerRequest;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomerResponse;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomersResponse;
import org.promgrammers.springbootbasic.domain.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerViewController {

    private final CustomerService customerService;

    @GetMapping
    public String save() {
        return "customers/saveForm";
    }

    @PostMapping
    public String save(@ModelAttribute("customer") CreateCustomerRequest request, RedirectAttributes redirectAttributes) {

        CustomerResponse customer = customerService.createCustomer(request);
        redirectAttributes.addAttribute("id", customer.customerId());

        return "redirect:/customers/{id}";
    }

    @GetMapping("list")
    public String findAll(Model model) {
        CustomersResponse customers = customerService.findAllCustomers();
        model.addAttribute("customers", customers);

        return "customers/customers";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable UUID id, Model model) {
        CustomerResponse customer = customerService.findCustomerById(id);
        model.addAttribute("customer", customer);

        return "customers/customer";
    }

    @PostMapping("/{id}")
    public String deleteById(@PathVariable UUID id) {
        customerService.deleteById(id);

        return "redirect:/customers/list";
    }
}
