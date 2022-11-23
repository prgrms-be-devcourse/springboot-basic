package com.programmers.customer.controller;

import com.programmers.customer.Customer;
import com.programmers.customer.dto.CustomerJoinForm;
import com.programmers.customer.service.CustomerService;
import com.programmers.voucher.service.VoucherService;
import com.programmers.voucher.voucher.Voucher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

import static com.programmers.message.ErrorMessage.ERROR_INPUT_MESSAGE;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final VoucherService voucherService;

    public CustomerController(CustomerService customerService, VoucherService voucherService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
    }

    @GetMapping
    public String findAllCustomers(Model model) {
        List<Customer> customers = customerService.findAll();

        model.addAttribute("customers", customers);
        return "customers";
    }

    @GetMapping("/new")
    public String joinPage() {
        return "new-customers";
    }

    @PostMapping("/new")
    public String join(CustomerJoinForm joinForm) {
        String name = joinForm.getName();
        String email = joinForm.getEmail();

        validateForJoin(name, email);

        customerService.join(name, email);

        return "redirect:/customers";
    }

    @GetMapping("/{customerId}")
    public String customerDetailPage(@PathVariable UUID customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        List<Voucher> vouchers = voucherService.searchVouchersByCustomerId(customerId);

        model.addAttribute("customer", customer);
        model.addAttribute("vouchers", vouchers);

        return "customerDetail";
    }

    @PostMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);

        return "redirect:/customers";
    }

    private void validateForJoin(String name, String email) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(email)) {
            throw new RuntimeException(ERROR_INPUT_MESSAGE.getMessage());
        }
    }

}
