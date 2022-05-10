package com.prgrms.voucher_manager.customer.controller;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.SimpleCustomer;
import com.prgrms.voucher_manager.customer.service.CustomerService;
import com.prgrms.voucher_manager.infra.facade.VoucherServiceFacade;
import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.controller.VoucherDto;
import com.prgrms.voucher_manager.voucher.service.VoucherService;
import com.prgrms.voucher_manager.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;
    private final VoucherService voucherService;
    private final VoucherServiceFacade voucherServiceFacade;
    private final WalletService walletService;


    public CustomerController(CustomerService customerService, VoucherService voucherService, VoucherServiceFacade voucherServiceFacade,  WalletService walletService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
        this.voucherServiceFacade = voucherServiceFacade;
        this.walletService = walletService;
    }

    @GetMapping("")
    public String customers(Model model) {
        List<CustomerDto> customers = customerService.findAllCustomer();
        model.addAttribute("customers", customers);
        return "customer/customers";
    }

    @GetMapping("/{customerId}")
    public String customer(@PathVariable UUID customerId, Model model) {
        CustomerDto customer = customerService.findCustomerById(customerId);


        model.addAttribute("customer", customer);
        return "customer/customer";
    }

    @GetMapping("/add")
    public String addCustomerForm() {
        return "customer/addCustomer";
    }

    @PostMapping("/add")
    public String addCustomer(@RequestParam String name,
            @RequestParam String email,
            RedirectAttributes redirectAttributes) {
        CustomerDto customer = customerService.createCustomer(name, email);
        redirectAttributes.addAttribute("customerId", customer.getCustomerId());
        return "redirect:/customers/{customerId}";
    }

    @GetMapping("/{customerId}/update")
    public String updateCustomerForm(@PathVariable UUID customerId, Model model) {

        CustomerDto customer = customerService.findCustomerById(customerId);

        model.addAttribute("customer", customer);
        return "customer/updateCustomer";

    }



    @PostMapping("/{customerId}/update")
    public String updateCustomer(@PathVariable UUID customerId,
                                 @RequestParam String name,
                                 @RequestParam String email) {
        Customer updatedCustomer = new SimpleCustomer(customerId, name, email, LocalDateTime.now());
        customerService.updateCustomer(customerId, email, name);
        return "redirect:/customers/{customerId}";
    }

    @GetMapping("/{customerId}/delete")
    public String deleteCustomer(@PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);
        return "redirect:/customers";
    }

    @GetMapping("/{customerId}/vouchers")
    public String findCustomerByVoucherType(@PathVariable UUID customerId, Model model) {
        List<VoucherDto> vouchers = voucherServiceFacade.findVoucherByCustomerId(customerId);

        model.addAttribute("vouchers", vouchers);
        return "customer/vouchers";

    }

    @GetMapping("/{customerId}/delete/vouchers/")
    public String deleteVoucher(@PathVariable UUID customerId, @RequestParam UUID voucherId) {
        walletService.deleteWallet(customerId, voucherId);
        return "redirect:/customers/{customerId}";
    }

    @GetMapping("/{customerId}/vouchers/add")
    public String addVoucherSelect(@PathVariable UUID customerId, Model model) {
        List<VoucherDto> vouchers = voucherService.getFindAllVoucher();

        model.addAttribute("vouchers", vouchers);
        model.addAttribute("customerId", customerId);
        return "customer/addVoucher";
    }

    @PostMapping("/{customerId}/vouchers/add")
    public String addVoucher(@PathVariable UUID customerId, @RequestParam UUID voucherId) {
        walletService.createWallet(customerId, voucherId);
        return "redirect:/customers/{customerId}";
    }


}
