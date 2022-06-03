package org.devcourse.voucher.application.customer.controller;

import org.devcourse.voucher.application.customer.controller.dto.CustomerRequest;
import org.devcourse.voucher.application.customer.controller.dto.CustomerResponse;
import org.devcourse.voucher.application.customer.model.Email;
import org.devcourse.voucher.application.customer.service.CustomerService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/customer")
public class WebCustomerController {

    private final CustomerService customerService;

    public WebCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public String customerMainPage(Model model, Pageable pageable) {
        List<CustomerResponse> customers = customerService.recallAllCustomer(pageable);
        model.addAttribute("customers", customers);
        return "customer/index";
    }

    @GetMapping("/new")
    public String customerCreatePage() {
        return "customer/new";
    }

    @PostMapping("/new")
    public String postCreateCustomer(CustomerRequest customerRequest) {
        customerService.createCustomer(
                customerRequest.getName(),
                new Email(customerRequest.getEmail())
        );
        return "redirect:";
    }

    @GetMapping("/{id}")
    public String customerUpdatePage(Model model, @PathVariable String id) {
        CustomerResponse customer = customerService.recallCustomerById(UUID.fromString(id));
        model.addAttribute("customer", customer);
        return "customer/update";
    }

    @PutMapping("/{id}")
    public String putUpdateCustomer(@PathVariable String id, CustomerRequest customerRequest) {
        customerService.updateCustomer(
                UUID.fromString(id),
                customerRequest.getName(),
                new Email(customerRequest.getEmail())
        );
        return "redirect:";
    }

    @DeleteMapping("/{id}")
    public String deleteRemoveCustomer(@PathVariable String id) {
        customerService.deleteCustomer(UUID.fromString(id));
        return "redirect:";
    }
}
