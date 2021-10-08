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
    private static final String USERID_MODEL_ATTRIBUTE = "id";
    private static final String USERNAME_MODEL_ATTRIBUTE = "username";
    private static final String ALIAS_MODEL_ATTRIBUTE = "alias";
    private static final String CUSTOMER_MODEL_ATTRIBUTE = "customer";
    private static final String CUSTOMERS_MODEL_ATTRIBUTE = "customers";
    private static final String VOUCHERS_MODEL_ATTRIBUTE = "vouchers";
    private static final String ERROR_MODEL_ATTRIBUTE = "error";

    private static final String TEMPLATE_CUSTOMER_INDEX = "customer/index";
    private static final String TEMPLATE_CUSTOMER_CREATE = "customer/create";
    private static final String TEMPLATE_CUSTOMER_READ = "customer/read";
    private static final String TEMPLATE_CUSTOMER_WALLET = "customer/wallet";
    private static final String TEMPLATE_CUSTOMER_LIST = "customer/list";

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
        return TEMPLATE_CUSTOMER_INDEX;
    }

    @GetMapping("/create")
    public String requestCreateCustomer(Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        return TEMPLATE_CUSTOMER_CREATE;
    }

    @PostMapping("/create")
    public String submitCreateCustomer(Customer.CreateRequest request,
                                       Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        model.addAttribute(USERNAME_MODEL_ATTRIBUTE, request.getUsername());
        model.addAttribute(ALIAS_MODEL_ATTRIBUTE, request.getAlias());

        try {
            Customer customer = basicCustomerService.create(request.getUsername(), request.getAlias());
            return "redirect:/customer/read?id=" + customer.getId();
        } catch (DuplicateKeyException ex) {
            model.addAttribute(ERROR_MODEL_ATTRIBUTE, "Duplicated username exists. Please try another username.");
            return TEMPLATE_CUSTOMER_CREATE;
        } catch (IllegalArgumentException ex) {
            model.addAttribute(ERROR_MODEL_ATTRIBUTE, ex.getMessage());
            return TEMPLATE_CUSTOMER_CREATE;
        }
    }

    @GetMapping("/read")
    public String getCustomer(@RequestParam(name = "id", required = false) Long id,
                              Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        model.addAttribute(USERID_MODEL_ATTRIBUTE, id);

        if (id != null && id > 0) {
            basicCustomerService.findById(id).ifPresentOrElse(
                    customer -> model.addAttribute(CUSTOMER_MODEL_ATTRIBUTE, customer),
                    () -> model.addAttribute(ERROR_MODEL_ATTRIBUTE, "No customer found."));
        }
        return TEMPLATE_CUSTOMER_READ;
    }

    @GetMapping("/list")
    public String getCustomers(Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        model.addAttribute(CUSTOMERS_MODEL_ATTRIBUTE, basicCustomerService.listAll());
        return TEMPLATE_CUSTOMER_LIST;
    }

    @GetMapping("/wallet")
    public String listVouchersInWallet(@RequestParam(value = "id", defaultValue = "") Long id,
                                       Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        if(id != null) {
            model.addAttribute(VOUCHERS_MODEL_ATTRIBUTE, customerVoucherService.findAllVoucherByCustomer(id));
            model.addAttribute(USERID_MODEL_ATTRIBUTE, id);
        }
        return TEMPLATE_CUSTOMER_WALLET;
    }
}
