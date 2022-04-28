package com.prgrms.vouchermanagement.customer;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.controller.VoucherDto;
import com.prgrms.vouchermanagement.wallet.VoucherWalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final VoucherWalletService walletService;

    public CustomerController(CustomerService customerService, VoucherWalletService walletService) {
        this.customerService = customerService;
        this.walletService = walletService;
    }

    @GetMapping
    public String findAll(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", CustomerDto.fromList(customers));
        return "customer/customers";
    }

    @GetMapping("/{customerId}")
    public String findById(@PathVariable UUID customerId, Model model) {
        Optional<Customer> optionalCustomer = customerService.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            return "error/404";
        }

        Customer findCustomer = optionalCustomer.get();
        List<Voucher> vouchers = walletService.findVoucherByCustomer(findCustomer.getCustomerId());

        model.addAttribute("customer", CustomerDto.from(findCustomer));
        model.addAttribute("vouchers", VoucherDto.fromList(vouchers));

        return "customer/customer";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("customer", CustomerDto.getEmptyCustomerDto());
        return "customer/addForm";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute CustomerDto customerDto, RedirectAttributes redirectAttributes) {
        UUID customerId = customerService.addCustomer(customerDto.getName(), customerDto.getEmail());
        redirectAttributes.addAttribute("customerId", customerId);
        return "redirect:/customers/{customerId}";
    }

    @PostMapping("/{customerId}/remove")
    public String removeCustomer(@PathVariable UUID customerId) {
        if (validateRegisteredCustomer(customerId)) {
            return "error/404";
        }

        customerService.removeCustomer(customerId);
        return "redirect:/customers";
    }

    private boolean validateRegisteredCustomer(UUID customerId) {
        return !customerService.isRegisteredCustomer(customerId);
    }
}
