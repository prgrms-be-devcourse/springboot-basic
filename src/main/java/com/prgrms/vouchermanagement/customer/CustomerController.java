package com.prgrms.vouchermanagement.customer;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.controller.VoucherResponse;
import com.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final VoucherService voucherService;

    public CustomerController(CustomerService customerService, VoucherService voucherService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
    }

    @GetMapping(value = "")
    public String findAll(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", CustomerResponse.fromList(customers));
        return "customer/customers";
    }

    @GetMapping(value = "/{customerId}")
    public String findById(@PathVariable Long customerId, Model model) {
        Optional<Customer> optionalCustomer = customerService.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            return "error/404";
        }

        Customer findCustomer = optionalCustomer.get();
        List<Voucher> vouchers = voucherService.findVoucherByCustomer(findCustomer.getCustomerId());

        model.addAttribute("customer", CustomerResponse.from(findCustomer));
        model.addAttribute("vouchers", VoucherResponse.fromList(vouchers));

        return "customer/customer";
    }

    @GetMapping(value = "/add")
    public String addForm() {
        return "customer/addForm";
    }

    @PostMapping(value = "/add")
    public String addCustomer(@ModelAttribute CreateCustomerRequest customerRequest, RedirectAttributes redirectAttributes) {
        Long customerId = customerService.addCustomer(customerRequest.getName(), customerRequest.getEmail());
        redirectAttributes.addAttribute("customerId", customerId);
        return "redirect:/customers/{customerId}";
    }

    @PostMapping(value = "/{customerId}/remove")
    public String removeCustomer(@PathVariable Long customerId) {
        if (customerService.removeCustomer(customerId)) {
            return "redirect:/customers";
        }

        return "error/404";
    }
}
