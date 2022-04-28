package org.prgrms.springbootbasic.controller.web;

import java.util.UUID;
import org.prgrms.springbootbasic.dto.RegisterCustomerRequest;
import org.prgrms.springbootbasic.service.CustomerService;
import org.prgrms.springbootbasic.util.DtoConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
public class WebCustomerController {

    private final CustomerService customerService;

    public WebCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String viewAllCustomer(Model model) {
        var customers = customerService.findAllCustomers();
        model.addAttribute("customerDtos", DtoConverter.toCustomerDtos(customers));
        return "customer/customers";
    }

    @GetMapping("/{customerId}")
    public String viewCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        var customer = customerService.findCustomer(customerId);
        model.addAttribute("customerDto", DtoConverter.toCustomerDto(customer));
        return "customer/customer";
    }

    @GetMapping("/new")
    public String viewRegisterForm(Model model) {
        model.addAttribute("registerCustomerRequest", new RegisterCustomerRequest());
        return "customer/new-customer";
    }

    @PostMapping("/new")
    public String registerCustomer(RegisterCustomerRequest registerCustomerRequest) {
        customerService.createCustomer(
            registerCustomerRequest.getName(),
            registerCustomerRequest.getEmail());
        return "redirect:/customers";
    }

    @PostMapping("/{customerId}/delete")
    public String deleteCustomer(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomer(customerId);
        return "redirect:/customers";
    }

}
