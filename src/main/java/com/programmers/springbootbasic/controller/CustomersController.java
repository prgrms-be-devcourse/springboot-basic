package com.programmers.springbootbasic.controller;

import com.programmers.springbootbasic.controller.dto.CreateCustomerRequest;
import com.programmers.springbootbasic.domain.Customer;
import com.programmers.springbootbasic.domain.Voucher;
import com.programmers.springbootbasic.service.CustomerService;
import com.programmers.springbootbasic.service.CustomerVoucherLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(path = "/customers")
public class CustomersController {

    private final CustomerService customerService;
    private final CustomerVoucherLogService customerVoucherLogService;

    @Autowired
    public CustomersController(CustomerService customerService, CustomerVoucherLogService customerVoucherLogService) {
        this.customerService = customerService;
        this.customerVoucherLogService = customerVoucherLogService;
    }

    @GetMapping
    public String showAllCustomers(Model model) {
        List<Customer> allCustomers = customerService.getAllCustomers();

        model.addAttribute("customers", allCustomers);

        return "/customers/customer-main";
    }

    @GetMapping("/new")
    public String showNewCustomerPage(Model model) {
        model.addAttribute("newCustomer", new CreateCustomerRequest());

        return "/customers/customer-new";
    }

    @PostMapping("/new")
    public String saveNewCustomer(CreateCustomerRequest request) {
        customerService.createCustomer(request.getCustomerId(), request.getName());

        return "redirect:/customers";
    }

    @GetMapping("/info/{id}")
    public String showInfoPage(@PathVariable("id") String customerId, Model model) {
        List<Voucher> vouchersOfCustomer = customerVoucherLogService.getVouchersOfCustomer(customerId);

        model.addAttribute("vouchers", vouchersOfCustomer);

        return "/customers/customer-info";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") String customerId) {
        customerService.deleteCustomerById(customerId);

        return "redirect:/customers";
    }

}
