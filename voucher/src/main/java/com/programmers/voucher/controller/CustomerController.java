package com.programmers.voucher.controller;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.service.customer.CustomerService;
import com.programmers.voucher.service.customer.CustomerVoucherService;
import org.springframework.dao.DuplicateKeyException;
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
    private final CustomerVoucherService customerVoucherService;

    static final List<String[]> links = new ArrayList<>();
    static final String LINKS_MODEL_ATTRIBUTE = "links";
    static {
        links.add(new String[]{"Main", "/"});
        links.add(new String[]{"Customer Main", "/customer"});
        links.add(new String[]{"Create User", "/customer/create"});
        links.add(new String[]{"Read User", "/customer/read"});
        links.add(new String[]{"List Users", "/customer/list"});
        links.add(new String[]{"My Wallet", "/customer/wallet"});
    }

    public CustomerController(CustomerService basicCustomerService, CustomerVoucherService customerVoucherService) {
        this.basicCustomerService = basicCustomerService;
        this.customerVoucherService = customerVoucherService;
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
    public String submitCreateCustomer(Customer.CreateRequest request,
                                       Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);

        if(request.getUsername().isBlank() || request.getAlias().isBlank()) {
            model.addAttribute("username", request.getUsername());
            model.addAttribute("alias", request.getAlias());
            model.addAttribute("error", "Required field should not be empty.");
            return "customer/create";
        }

        try {
            Customer customer = basicCustomerService.create(request.getUsername(), request.getAlias());
            return "redirect:/customer/read?id=" + customer.getId();
        } catch (DuplicateKeyException ex) {
            model.addAttribute("username", request.getUsername());
            model.addAttribute("alias", request.getAlias());
            model.addAttribute("error", "Duplicated username exists. Please try another username.");
            return "customer/create";
        }
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

    @GetMapping("/wallet")
    public String listVouchersInWallet(@RequestParam(value = "id", defaultValue = "") Long id,
                                       Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        model.addAttribute("id", id);
        if(id != null) model.addAttribute("vouchers", customerVoucherService.findAllVoucherByCustomer(id));
        return "customer/wallet";
    }
}
