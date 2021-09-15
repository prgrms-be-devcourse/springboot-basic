package org.prgrms.kdt.customer.presentation;

import org.prgrms.kdt.customer.application.CustomerService;
import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.customer.domain.vo.Email;
import org.prgrms.kdt.customer.dto.CustomerSignDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/customer/sign")
    public String signCustomer(Model model) {
        CustomerSignDto dto = new CustomerSignDto();
        model.addAttribute("customerSign", dto);
        return "/customer/sign-customer";
    }

    @PostMapping("/customer/sign")
    public String signCustomer(CustomerSignDto dto) {
        customerService.createCustomer(dto);
        return "redirect:/index";
    }

    @GetMapping("/customers")
    public String manageCustomer(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "/customer/manage-customer";
    }

    @PostMapping("/customers")
    public String manageCustomer(Email email) {
        customerService.changeRole(email);
        return "redirect:/customers";
    }
}
