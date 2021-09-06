package com.programmers.voucher.controller;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.service.customer.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService basicCustomerService;

    static final List<String[]> links = new ArrayList<>();
    static final String LINKS_MODEL_ATTRIBUTE = "links";
    static {
        links.add(new String[]{"Main", "/customer"});
        links.add(new String[]{"Create User", "/customer/create"});
        links.add(new String[]{"Read User", "/customer/read"});
        links.add(new String[]{"List Users", "/customer/list"});
    }

    public CustomerController(CustomerService basicCustomerService) {
        this.basicCustomerService = basicCustomerService;
    }

    @GetMapping
    public String customerConsole(Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        return "customer/index";
    }

    @GetMapping("/create")
    public String requestCreateCustomer(Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        return "customer/create";
    }

    @PostMapping("/create")
    public String submitCreateCustomer(@RequestParam(name="username", defaultValue = "") String username,
                                       @RequestParam(name="alias", defaultValue = "") String alias,
                                       Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);

        if(username.isBlank() || alias.isBlank()) {
            model.addAttribute("username", username);
            model.addAttribute("alias", alias);
            model.addAttribute("error", "Required field should not be empty.");
            return "customer/create";
        }

        Customer customer = basicCustomerService.create(username, alias);
        return "redirect:/customer/read?id=" + customer.getId();
    }

    @GetMapping("/read")
    public String getCustomer(@RequestParam(name = "id", required = false) Long id,
                              Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        model.addAttribute("id", id);

        if (id == null || id < 1) return "customer/read";

        basicCustomerService.findById(id).ifPresentOrElse(
                customer -> model.addAttribute("customer", customer),
                () -> model.addAttribute("error", "No customer found."));

        return "customer/read";
    }

    @GetMapping("/list")
    public String getCustomers(Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        model.addAttribute("customers", basicCustomerService.listAll());
        return "customer/list";
    }
}
